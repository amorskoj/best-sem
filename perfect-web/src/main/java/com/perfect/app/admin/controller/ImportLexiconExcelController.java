package com.perfect.app.admin.controller;

import com.perfect.commons.web.WebContextSupport;
import com.perfect.dto.keyword.LexiconDTO;
import com.perfect.service.LexiconService;
import com.perfect.utils.excel.XSSFReadUtils;
import com.perfect.utils.excel.XSSFSheetHandler;
import com.perfect.utils.redis.JRedisUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import static com.perfect.commons.constants.MongoEntityConstants.TRADE_KEY;

/**
 * Created by baizz on 2014-10-9.
 * 2014-12-2 refactor
 */
@RestController
@Scope("prototype")
@RequestMapping("/admin/lexicon")
public class ImportLexiconExcelController extends WebContextSupport {

    private static final String TMP_DIR = System.getProperty("java.io.tmpdir");
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");

    private static String trade;

    @Resource
    private LexiconService lexiconService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void importLexicon(HttpServletRequest request, HttpServletResponse response,
                              @RequestParam(value = "excelFile") MultipartFile[] files)
            throws Exception {

        String tmpDirPath = TMP_DIR + FILE_SEPARATOR;

        //上传临时文件
        String tmpFile = "";
        String fileName = "";
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                fileName = file.getOriginalFilename();
                File _file = new File(tmpDirPath, fileName);
                FileUtils.copyInputStreamToFile(file.getInputStream(), _file);
                tmpFile = tmpDirPath + fileName;
            }
        }
        if (StringUtils.isEmpty(tmpFile)) {
            return;
        }

        if (!"xlsx".equals(com.google.common.io.Files.getFileExtension(fileName))) {
            Files.delete(Paths.get(tmpFile));
            return;
        }

        trade = fileName.substring(0, 2);
        Path file = Paths.get(tmpFile);
        final Map<String, LexiconDTO> map = new HashMap<>(1 << 16);
        XSSFReadUtils.read(file, new XSSFSheetHandler() {
            @Override
            protected void rowMap(int sheetIndex, int rowIndex, List<Object> row) {
                LexiconDTO lexiconDTO = new LexiconDTO();
                lexiconDTO.setTrade(trade);
                if (!row.isEmpty() && row.size() == 3) {
                    String keyword = row.get(2).toString();
                    lexiconDTO.setCategory(row.get(0).toString());
                    lexiconDTO.setGroup(row.get(1).toString());
                    lexiconDTO.setKeyword(keyword);
                    map.put(trade + keyword, lexiconDTO);
                }

            }

        });
        List<LexiconDTO> list = new ArrayList<>(map.values());

        //delete tmpFile
        Files.delete(Paths.get(tmpFile));

        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors() + 1);
        try {
            LexiconTask task = new LexiconTask(list, 0, list.size() - 1);
            pool.invoke(task);
        } finally {
            pool.shutdown();
        }
//        response.getWriter().write("<script type='text/javascript'>parent.callback('true')</script>");
        writeData(SUCCESS, response, fileName);
        Jedis jc = JRedisUtils.get();
        if (jc.exists(TRADE_KEY)) {
            jc.del(TRADE_KEY);
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView deleteLexiconByTrade(HttpServletResponse response,
                                             @RequestParam(value = "trade") String trade,
                                             @RequestParam(value = "category", required = false) String category)
            throws IOException {
        lexiconService.deleteLexiconByTrade(trade, category);
        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> result = new HashMap<String, Object>() {{
            put("status", true);
        }};
        jsonView.setAttributesMap(result);
//        return new ModelAndView(jsonView);
        writeData(SUCCESS, response, SUCCESS);
        Jedis jc = JRedisUtils.get();
        if (jc.exists(TRADE_KEY)) {
            jc.del(TRADE_KEY);
        }
        return null;
    }

    class LexiconTask extends RecursiveAction {
        private int first;
        private int last;
        private List<LexiconDTO> entityList;

        LexiconTask(List<LexiconDTO> entityList, int first, int last) {
            this.entityList = entityList;
            this.first = first;
            this.last = last;
        }

        @Override
        protected void compute() {
            if (last - first < 3_000) {
                List<LexiconDTO> list = new ArrayList<>();
                for (int i = first; i <= last; i++) {
                    LexiconDTO dto = entityList.get(i);
                    list.add(dto);
                }
                lexiconService.save(list);
            } else {
                int middle = (last - first) / 2;
                LexiconTask task1 = new LexiconTask(entityList, first, middle + first);
                LexiconTask task2 = new LexiconTask(entityList, middle + first + 1, last);
                invokeAll(task1, task2);
            }
        }
    }

}