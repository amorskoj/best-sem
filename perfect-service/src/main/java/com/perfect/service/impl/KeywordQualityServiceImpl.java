package com.perfect.service.impl;

import com.google.common.collect.Lists;
import com.google.common.primitives.Bytes;
import com.perfect.api.baidu.BaiduServiceSupport;
import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.GetKeyword10QualityRequest;
import com.perfect.autosdk.sms.v3.GetKeyword10QualityResponse;
import com.perfect.autosdk.sms.v3.KeywordService;
import com.perfect.autosdk.sms.v3.Quality10Type;
import com.perfect.core.AppContext;
import com.perfect.dao.account.AccountManageDAO;
import com.perfect.dao.keyword.KeywordQualityDAO;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;
import com.perfect.dto.keyword.KeywordReportDTO;
import com.perfect.dto.keyword.QualityDTO;
import com.perfect.service.KeywordQualityService;
import com.perfect.utils.DateUtils;
import com.perfect.utils.TopN;
import com.perfect.utils.json.JSONUtils;
import com.perfect.utils.json.SerializeUtils;
import com.perfect.utils.redis.JRedisUtils;
import com.perfect.vo.KeywordQualityReportVO;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

/**
 * Created by baizz on 2014-08-16.
 * 2014-12-8 refactor
 */
@Service("keywordQualityService")
public class KeywordQualityServiceImpl implements KeywordQualityService {

    private static final String DEFAULT_DELIMITER = ",";
    private static final String DEFAULT_END = "\r\n";
    private static final byte commonCSVHead[] = {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};
    private static final List<Integer> qList = Lists.newArrayList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    private static String key = "";

    @Resource
    private AccountManageDAO accountManageDAO;

    @Resource
    private KeywordQualityDAO keywordQualityDAO;

    @Override
    public Map<String, Object> find(String redisKey, String fieldName, int n, int skip, int sort) {
        fieldName = "pc" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        List<KeywordReportDTO> list = keywordQualityDAO.findYesterdayKeywordReport();
        if (list.size() == 0)
            return Collections.<String, Object>emptyMap();

        //getYesterdayAllKeywordId
        List<Long> keywordIds = new ArrayList<>();
        ForkJoinPool forkJoinPool1 = new ForkJoinPool();
        try {
            KeywordIdTask task = new KeywordIdTask(list, 0, list.size());
            Future<List<Long>> result = forkJoinPool1.submit(task);
            keywordIds = result.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            forkJoinPool1.shutdown();
        }

        Map<String, KeywordReportDTO> map = new LinkedHashMap<>();
        ForkJoinPool forkJoinPool2 = new ForkJoinPool();
        try {
            CalculateTask task = new CalculateTask(list, 0, list.size());
            Future<Map<String, KeywordReportDTO>> result = forkJoinPool2.submit(task);
            map = result.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            forkJoinPool2.shutdown();
        }

        list = new ArrayList<>(map.values());
        QualityDTO allQualityData = getQualityData(list);

        //获取关键词质量度
        List<Quality10Type> quality10Types = getQuality10Type(redisKey, keywordIds);

        Map<Integer, List<KeywordReportDTO>> tempMap = new HashMap<>();
        IntStream.rangeClosed(0, 10).forEach(i -> tempMap.put(i, new ArrayList<>()));

        final Map<String, KeywordReportDTO> finalMap = map;
        quality10Types.stream().forEach(q -> tempMap.get(q.getPcQuality()).add(finalMap.get(q.getId().toString())));

        Map<String, Object> results = new HashMap<>();
        List<QualityDTO> qualityList = new ArrayList<>();
        List<KeywordQualityReportVO> reportList = new ArrayList<>();


        final List<KeywordReportDTO> finalList = list;
        final String finalFieldName = fieldName;
        qList.stream().forEach(i -> {
            List<KeywordReportDTO> tempList = new ArrayList<>();
            tempMap.get(i).stream().filter(o -> o != null).forEach(tempList::add);
            if (!tempList.isEmpty()) {

                //质量度级别信息计算
                QualityDTO qualityDTO = getQualityData(tempList);
                if (qualityDTO.getCost() > 0) {
                    Double cost = new BigDecimal(qualityDTO.getCost()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    qualityDTO.setCost(cost);
                }
                if (qualityDTO.getImpression() > 0) {
                    Double ctr = (qualityDTO.getClick() + 0.0) / qualityDTO.getImpression();
                    ctr = new BigDecimal(ctr * 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    qualityDTO.setCtr(ctr);
                }
                if (qualityDTO.getClick() > 0) {
                    Double cpc = qualityDTO.getCost() / qualityDTO.getClick();
                    cpc = new BigDecimal(cpc * 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    qualityDTO.setCpc(cpc);
                }
                Double keywordQtyRate = (tempList.size() + 0.0) / finalList.size();
                keywordQtyRate = new BigDecimal(keywordQtyRate * 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                qualityDTO.setKeywordQtyRate(keywordQtyRate);

                Double impressionRate = 0.0;
                if (allQualityData.getImpression() > 0) {
                    impressionRate = (qualityDTO.getImpression() + 0.0) / allQualityData.getImpression();
                    impressionRate = new BigDecimal(impressionRate * 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                }
                qualityDTO.setImpressionRate(impressionRate);

                Double clickRate = 0.0;
                if (allQualityData.getClick() > 0) {
                    clickRate = (qualityDTO.getClick() + 0.0) / allQualityData.getClick();
                    clickRate = new BigDecimal(clickRate * 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                }
                qualityDTO.setClickRate(clickRate);

                Double costRate = 0.0;
                if (allQualityData.getCost() > 0) {
                    costRate = (qualityDTO.getCost() + 0.0) / allQualityData.getCost();
                    costRate = new BigDecimal(costRate * 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                }
                qualityDTO.setCostRate(costRate);

                Double conversionRate = 0.0;
                if (allQualityData.getConversion() > 0) {
                    conversionRate = (qualityDTO.getConversion() + 0.0) / allQualityData.getConversion();
                    conversionRate = new BigDecimal(conversionRate * 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                }
                qualityDTO.setConversionRate(conversionRate);

                qualityDTO.setGrade(i);
                qualityList.add(qualityDTO);

                //每个质量度下具体的关键词信息
                Optional<KeywordReportDTO[]> keywordReportTopNDataOptional =
                        Optional.ofNullable(TopN.getTopN(tempList.toArray(new KeywordReportDTO[tempList.size()]), n, finalFieldName, sort));

                if (keywordReportTopNDataOptional.isPresent()) {
                    KeywordReportDTO topNData[] = keywordReportTopNDataOptional.get();
                    if ((skip + 1) * n > topNData.length) {
                        List<KeywordReportDTO> data = new ArrayList<>();
//                    for (int j = skip * n; j < topNData.length; j++) {
//                        data.add(topNData[j]);
//                    }
                        data.addAll(Arrays.asList(topNData).subList(skip * n, topNData.length));
                        reportList.add(new KeywordQualityReportVO(i, data));
                    } else {
                        KeywordReportDTO arrData[] = new KeywordReportDTO[n];
                        System.arraycopy(topNData, skip * n, arrData, 0, n);
                        reportList.add(new KeywordQualityReportVO(i, Arrays.asList(arrData)));
                    }
                }
            }
        });

        results.put("redisKey", key);
        results.put("qualityDTO", JSONUtils.getJsonObjectArray(qualityList));
        results.put("report", JSONUtils.getJsonObjectArray(reportList));

        return results;
    }

    @Override
    public List<Quality10Type> getKeyword10Quality(List<Long> keywordIds) {
        BaiduAccountInfoDTO baiduAccount = accountManageDAO.findByBaiduUserId(AppContext.getAccountId());
        CommonService commonService = BaiduServiceSupport.getCommonService(baiduAccount.getBaiduUserName(), baiduAccount.getBaiduPassword(), baiduAccount.getToken());
        if (commonService == null) {
            return Collections.<Quality10Type>emptyList();
        }

        List<Quality10Type> quality10TypeList = new ArrayList<>();
        try {
            KeywordService keywordService = commonService.getService(KeywordService.class);
            int _max = 10_000, toIndex = _max;
            if (keywordIds.size() > _max) {
                for (int i = 0, s = keywordIds.size(); i < s; i += _max) {
                    if (i + _max > s) {
                        toIndex = s;
                    } else if (i > 0) {
                        toIndex = i + _max;
                    }
                    List<Long> tmpKeywordIdList = keywordIds.subList(i, toIndex);
                    GetKeyword10QualityRequest request = new GetKeyword10QualityRequest();
                    request.setIds(tmpKeywordIdList);
                    request.setDevice(0);
                    request.setType(11);
                    request.setHasScale(false);
                    GetKeyword10QualityResponse response = keywordService.getKeyword10Quality(request);
                    if (response != null) {
                        quality10TypeList.addAll(response.getKeyword10Quality());
                    }
                }
            } else {
                GetKeyword10QualityRequest request = new GetKeyword10QualityRequest();
                request.setIds(keywordIds);
                request.setDevice(0);
                request.setType(11);
                request.setHasScale(false);
                GetKeyword10QualityResponse response = keywordService.getKeyword10Quality(request);
                if (response != null) {
                    quality10TypeList.addAll(response.getKeyword10Quality());
                }
            }
            return quality10TypeList;
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return Collections.<Quality10Type>emptyList();
    }

    @Override
    public void downloadQualityCSV(String redisKey, OutputStream os) {
        List<KeywordReportDTO> list = keywordQualityDAO.findYesterdayKeywordReport();
        if (list.size() == 0)
            return;

        List<Long> keywordIds = new ArrayList<>();
        ForkJoinPool forkJoinPool1 = new ForkJoinPool();
        try {
            KeywordIdTask task = new KeywordIdTask(list, 0, list.size());
            Future<List<Long>> result = forkJoinPool1.submit(task);
            keywordIds = result.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            forkJoinPool1.shutdown();
        }

        Map<String, KeywordReportDTO> map = new LinkedHashMap<>();
        ForkJoinPool forkJoinPool2 = new ForkJoinPool();
        try {
            CalculateTask task = new CalculateTask(list, 0, list.size());
            Future<Map<String, KeywordReportDTO>> result = forkJoinPool2.submit(task);
            map = result.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            forkJoinPool2.shutdown();
        }

        list = new ArrayList<>(map.values());
        QualityDTO allQualityData = getQualityData(list);

        //获取关键词质量度
        List<Quality10Type> quality10Types = getQuality10Type(redisKey, keywordIds);

        Map<Integer, List<KeywordReportDTO>> tempMap = new HashMap<>();
        IntStream.rangeClosed(0, 10).forEach(i -> tempMap.put(i, new ArrayList<>()));

        final Map<String, KeywordReportDTO> finalMap = map;
        quality10Types.stream().forEach(q -> tempMap.get(q.getPcQuality()).add(finalMap.get(q.getId().toString())));

        Map<Integer, QualityDTO> qualityDTOMap = new HashMap<>();
        List<KeywordQualityReportVO> reportList = new ArrayList<>();

        final List<KeywordReportDTO> finalList = list;
        qList.stream().forEach(i -> {
            List<KeywordReportDTO> tempList = new ArrayList<>();
            tempMap.get(i).stream().filter(o -> o != null).forEach(tempList::add);
            if (!tempList.isEmpty()) {

                //质量度级别信息计算
                QualityDTO qualityDTO = getQualityData(tempList);
                if (qualityDTO.getCost() > 0) {
                    Double cost = new BigDecimal(qualityDTO.getCost()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    qualityDTO.setCost(cost);
                }
                if (qualityDTO.getImpression() > 0) {
                    Double ctr = (qualityDTO.getClick() + .0) / qualityDTO.getImpression();
                    ctr = new BigDecimal(ctr * 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    qualityDTO.setCtr(ctr);
                }
                if (qualityDTO.getClick() > 0) {
                    Double cpc = qualityDTO.getCost() / qualityDTO.getClick();
                    cpc = new BigDecimal(cpc * 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    qualityDTO.setCpc(cpc);
                }
                Double keywordQtyRate = (tempList.size() + 0.0) / finalList.size();
                keywordQtyRate = new BigDecimal(keywordQtyRate * 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                qualityDTO.setKeywordQtyRate(keywordQtyRate);

                Double impressionRate = 0.0;
                if (allQualityData.getImpression() > 0) {
                    impressionRate = (qualityDTO.getImpression() + 0.0) / allQualityData.getImpression();
                    impressionRate = new BigDecimal(impressionRate * 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                }
                qualityDTO.setImpressionRate(impressionRate);

                Double clickRate = 0.0;
                if (allQualityData.getClick() > 0) {
                    clickRate = (qualityDTO.getClick() + 0.0) / allQualityData.getClick();
                    clickRate = new BigDecimal(clickRate * 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                }
                qualityDTO.setClickRate(clickRate);

                Double costRate = 0.0;
                if (allQualityData.getCost() > 0) {
                    costRate = (qualityDTO.getCost() + 0.0) / allQualityData.getCost();
                    costRate = new BigDecimal(costRate * 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                }
                qualityDTO.setCostRate(costRate);

                Double conversionRate = 0.0;
                if (allQualityData.getConversion() > 0) {
                    conversionRate = (qualityDTO.getConversion() + 0.0) / allQualityData.getConversion();
                    conversionRate = new BigDecimal(conversionRate * 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                }
                qualityDTO.setConversionRate(conversionRate);

                qualityDTO.setGrade(i);
                qualityDTOMap.put(i, qualityDTO);

                //每个质量度下具体的关键词信息
                Optional<KeywordReportDTO[]> keywordReportTopNDataOptional =
                        Optional.ofNullable(TopN.getTopN(tempList.toArray(new KeywordReportDTO[tempList.size()]), tempList.size(), "pcImpression", -1));
                if (keywordReportTopNDataOptional.isPresent()) {
                    reportList.add(new KeywordQualityReportVO(i, Arrays.asList(keywordReportTopNDataOptional.get())));
                }
            }
        });


        //CSV file
        try {
            os.write(Bytes.concat(commonCSVHead, ("质量度" +
                    DEFAULT_DELIMITER + "关键词" +
                    DEFAULT_DELIMITER + "展现" +
                    DEFAULT_DELIMITER + "点击" +
                    DEFAULT_DELIMITER + "点击率" +
                    DEFAULT_DELIMITER + "消费" +
                    DEFAULT_DELIMITER + "平均点击价格" +
                    DEFAULT_DELIMITER + "转化" +
                    DEFAULT_END).getBytes(StandardCharsets.UTF_8)));
            for (KeywordQualityReportVO reportDTO : reportList) {
                Integer grade = reportDTO.getGrade();
                QualityDTO qualityDTO = qualityDTOMap.get(grade);
                String bytes = (grade + DEFAULT_DELIMITER +
                        qualityDTO.getKeywordQty() + "(" + qualityDTO.getKeywordQtyRate() + "%)" + DEFAULT_DELIMITER +
                        qualityDTO.getImpression() + "(" + qualityDTO.getImpressionRate() + "%)" + DEFAULT_DELIMITER +
                        qualityDTO.getClick() + "(" + qualityDTO.getClickRate() + "%)" + DEFAULT_DELIMITER +
                        qualityDTO.getCtr() + "%" + DEFAULT_DELIMITER +
                        qualityDTO.getCost() + "(" + qualityDTO.getCostRate() + "%)" + DEFAULT_DELIMITER +
                        qualityDTO.getCpc() + DEFAULT_DELIMITER +
                        qualityDTO.getConversion() + "(" + qualityDTO.getConversionRate() + "%)" + DEFAULT_END);
                os.write(Bytes.concat(commonCSVHead, bytes.getBytes(StandardCharsets.UTF_8)));
                os.write(Bytes.concat(commonCSVHead, ("" +
                        DEFAULT_DELIMITER + "关键词" +
                        DEFAULT_DELIMITER + "" +
                        DEFAULT_DELIMITER + "" +
                        DEFAULT_DELIMITER + "" +
                        DEFAULT_DELIMITER + "" +
                        DEFAULT_DELIMITER + "" +
                        DEFAULT_DELIMITER + "" +
                        DEFAULT_END).getBytes(StandardCharsets.UTF_8)));

                for (KeywordReportDTO entity : reportDTO.getReportList()) {
                    bytes = (grade + DEFAULT_DELIMITER +
                            entity.getKeywordName() + DEFAULT_DELIMITER +
                            entity.getPcImpression() + DEFAULT_DELIMITER +
                            entity.getPcClick() + DEFAULT_DELIMITER +
                            qualityDTO.getCtr() + "%" + DEFAULT_DELIMITER +
                            entity.getPcCost() + DEFAULT_DELIMITER +
                            entity.getPcCpc() + DEFAULT_DELIMITER +
                            entity.getPcConversion() + DEFAULT_END);
                    os.write(Bytes.concat(commonCSVHead, bytes.getBytes(StandardCharsets.UTF_8)));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private List<Quality10Type> getQuality10Type(String redisKey, List<Long> keywordIds) {
        List<Quality10Type> quality10Types = new ArrayList<>();
        if (redisKey != null && redisKey.length() > 0) {
            Jedis jedis = null;
            try {
                jedis = JRedisUtils.get();
                if (jedis.ttl(redisKey) == -1) {
                    quality10Types = getKeyword10Quality(keywordIds);
                    redisKey = AppContext.getUser() + AppContext.getAccountId() + DateUtils.getYesterday().getTime();
                    saveToRedis(redisKey, quality10Types);
                } else {
                    byte[] bytes = jedis.get(redisKey.getBytes(StandardCharsets.UTF_8));
                    quality10Types = SerializeUtils.deSerializeList(bytes, Quality10Type.class);
                }
            } catch (final Exception e) {
                e.printStackTrace();
            } finally {
                if (jedis != null) {
                    JRedisUtils.returnJedis(jedis);
                }
            }
        } else {
            quality10Types = getKeyword10Quality(keywordIds);
            redisKey = AppContext.getUser() + AppContext.getAccountId() + DateUtils.getYesterday().getTime();
            saveToRedis(redisKey, quality10Types);
        }

        key = redisKey;

        return quality10Types;
    }

    private QualityDTO getQualityData(List<KeywordReportDTO> list) {
        QualityDTO qualityDTO = new QualityDTO(list.size(), .0, 0, .0, 0, .0, .0, .0, .0, .0, .0, .0);
        list.stream().forEach(o -> {
            qualityDTO.setImpression(qualityDTO.getImpression() + o.getPcImpression());
            qualityDTO.setClick(qualityDTO.getClick() + o.getPcClick());
            BigDecimal bigDecimal = new BigDecimal(qualityDTO.getCost());
            qualityDTO.setCost(bigDecimal.add(o.getPcCost()).doubleValue());
            qualityDTO.setConversion(qualityDTO.getConversion() + o.getPcConversion());
        });
        return qualityDTO;
    }

    private void saveToRedis(String id, List<Quality10Type> list) {
        byte key[] = id.getBytes(StandardCharsets.UTF_8);
        byte value[] = SerializeUtils.serialize(list);
        Jedis jedis = null;
        try {
            jedis = JRedisUtils.get();
            jedis.set(key, value);
            jedis.expire(id, 10_800);
        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                JRedisUtils.returnJedis(jedis);
            }
        }
    }

    class KeywordIdTask extends RecursiveTask<List<Long>> {

        private int start;
        private int end;
        private List<KeywordReportDTO> list;

        KeywordIdTask(List<KeywordReportDTO> list, int start, int end) {
            this.start = start;
            this.end = end;
            this.list = list;
        }

        @Override
        protected List<Long> compute() {
            List<Long> ids = new ArrayList<>();
            if (end - start < 1_000) {
                for (int i = start; i < end; i++) {
                    ids.add(list.get(i).getKeywordId());
                }
            } else {
                int middle = (end - start) / 2;
                KeywordIdTask task1 = new KeywordIdTask(list, start, start + middle);
                KeywordIdTask task2 = new KeywordIdTask(list, start + middle, end);

                invokeAll(task1, task2);

                ids.clear();
                ids.addAll(task1.join());
                ids.addAll(task2.join());
            }
            return ids;
        }
    }

    class CalculateTask extends RecursiveTask<Map<String, KeywordReportDTO>> {

        private static final int threshold = 1_000;

        private int start;
        private int end;
        private List<KeywordReportDTO> list;

        CalculateTask(List<KeywordReportDTO> list, int start, int end) {
            this.start = start;
            this.end = end;
            this.list = list;
        }

        @Override
        protected Map<String, KeywordReportDTO> compute() {
            Map<String, KeywordReportDTO> map = new HashMap<>();
            if (end - start < threshold) {
                for (int i = start; i < end; i++) {
                    KeywordReportDTO vo = list.get(i);
                    map.put(vo.getKeywordId().toString(), vo);
//                    String keywordId = vo.getKeywordId().toString();
//                    KeywordReportDTO _vo = map.get(keywordId);
//                    if (_vo != null) {
//                        _vo.setPcImpression(_vo.getPcImpression() + vo.getPcImpression());
//                        _vo.setPcClick(_vo.getPcClick() + vo.getPcClick());
//                        _vo.setPcCtr(0.);
//                        _vo.setPcCost(_vo.getPcCost() + vo.getPcCost());
//                        _vo.setPcCpc(0.);
//                        _vo.setPcPosition(_vo.getPcPosition() + vo.getPcPosition());
//                        _vo.setPcConversion(_vo.getPcConversion() + vo.getPcConversion());
//                        map.put(keywordId, _vo);
//                    } else {
//                        map.put(keywordId, vo);
//                    }
                }
            } else {
                int middle = (end - start) / 2;
                CalculateTask task1 = new CalculateTask(list, start, start + middle);
                CalculateTask task2 = new CalculateTask(list, start + middle, end);

                invokeAll(task1, task2);

                //map合并处理
                map.clear();
                map.putAll(task1.join());
                map.putAll(task2.join());
//                map = merge(task1.join(), task2.join());
            }
            return map;
        }

        /*@Deprecated
        private Map<String, KeywordReportDTO> merge(Map<String, KeywordReportDTO> map1, Map<String, KeywordReportDTO> map2) {
            Map<String, KeywordReportDTO> _map = new HashMap<>();
            for (Iterator<Map.Entry<String, KeywordReportDTO>> iterator1 = map1.entrySet().iterator(); iterator1.hasNext(); ) {
                KeywordReportDTO vo = iterator1.next().getValue();
                for (Iterator<Map.Entry<String, KeywordReportDTO>> iterator2 = map2.entrySet().iterator(); iterator2.hasNext(); ) {
                    KeywordReportDTO _vo = iterator2.next().getValue();
                    if (_vo.getKeywordId().compareTo(vo.getKeywordId()) == 0) {
                        _vo.setPcImpression(_vo.getPcImpression() + vo.getPcImpression());
                        _vo.setPcClick(_vo.getPcClick() + vo.getPcClick());
                        _vo.setPcCtr(0.);
                        BigDecimal bigDecimal = new BigDecimal(0.);
                        _vo.setPcCost(bigDecimal.add(_vo.getPcCost()).add(vo.getPcCost()));
                        _vo.setPcCpc(new BigDecimal(0.));
                        _vo.setPcPosition(_vo.getPcPosition() + vo.getPcPosition());
                        _vo.setPcConversion(_vo.getPcConversion() + vo.getPcConversion());
                        _map.put(_vo.getKeywordId().toString(), _vo);
                        iterator1.remove();
                        iterator2.remove();
                        break;
                    }
                }
            }

            for (Map.Entry<String, KeywordReportDTO> entry : map1.entrySet()) {
                KeywordReportDTO vo = entry.getValue();
                _map.put(vo.getKeywordId().toString(), vo);
            }

            for (Map.Entry<String, KeywordReportDTO> entry : map2.entrySet()) {
                KeywordReportDTO vo = entry.getValue();
                _map.put(vo.getKeywordId().toString(), vo);
            }

            return _map;
        }*/

    }

}