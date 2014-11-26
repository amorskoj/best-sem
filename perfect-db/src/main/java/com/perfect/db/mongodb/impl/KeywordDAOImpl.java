package com.perfect.db.mongodb.impl;

import com.perfect.commons.constants.LogStatusConstant;
import com.perfect.commons.constants.MongoEntityConstants;
import com.perfect.core.AppContext;
import com.perfect.dao.*;
import com.perfect.dao.account.AccountManageDAO;
import com.perfect.db.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.db.mongodb.base.BaseMongoTemplate;
import com.perfect.dto.backup.KeyWordBackUpDTO;
import com.perfect.dto.keyword.KeywordDTO;
import com.perfect.entity.*;
import com.perfect.entity.backup.KeyWordBackUpEntity;
import com.perfect.utils.Pager;
import com.perfect.utils.PagerInfo;
import com.perfect.utils.PaginationParam;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by baizz on 2014-07-07.
 */
@Repository("keywordDAO")
public class KeywordDAOImpl extends AbstractUserBaseDAOImpl<KeywordDTO, Long> implements KeywordDAO {

    @Resource
    private LogDAO logDao;

    @Resource
    private KeyWordBackUpDAO keyWordBackUpDAO;

    @Resource
    private AccountManageDAO<BaiduAccountInfoEntity> accountManageDAO;

    @Override
    public String getId() {
        return MongoEntityConstants.KEYWORD_ID;
    }

    public List<Long> getKeywordIdByAdgroupId(Long adgroupId) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Query query = new BasicQuery("{}", "{" + MongoEntityConstants.KEYWORD_ID + " : 1}");
        query.addCriteria(Criteria.where(MongoEntityConstants.ADGROUP_ID).is(adgroupId));
        List<KeywordEntity> list = mongoTemplate.find(query, KeywordEntity.class);
        List<Long> keywordIds = new ArrayList<>(list.size());
        for (KeywordEntity type : list)
            keywordIds.add(type.getKeywordId());
        return keywordIds;
    }

    public List<KeywordDTO> getKeywordByAdgroupId(Long adgroupId, Map<String, Object> params, int skip, int limit) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Query query = new Query();
        Criteria criteria = Criteria.where(MongoEntityConstants.ADGROUP_ID).is(adgroupId);
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> entry : params.entrySet())
                criteria.and(entry.getKey()).is(entry.getValue());
        }
        query.addCriteria(criteria);
        query.with(new PageRequest(skip, limit, new Sort(Sort.Direction.DESC, "price")));
        List<KeywordEntity> _list = mongoTemplate.find(query, KeywordEntity.class, MongoEntityConstants.TBL_KEYWORD);
        return _list;
    }


    //根据mongoID查询
    public List<KeywordDTO> getKeywordByAdgroupId(String adgroupId, Map<String, Object> params, int skip, int limit) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Query query = new Query();
        Criteria criteria = Criteria.where(MongoEntityConstants.OBJ_ADGROUP_ID).is(adgroupId);
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> entry : params.entrySet())
                criteria.and(entry.getKey()).is(entry.getValue());
        }
        query.addCriteria(criteria);
        query.with(new PageRequest(skip, limit, new Sort(Sort.Direction.DESC, "price")));
        List<KeywordEntity> _list = mongoTemplate.find(query, KeywordEntity.class, MongoEntityConstants.TBL_KEYWORD);
        return _list;
    }

    @Override
    public List<KeywordDTO> findByAgroupId(Long oid) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        List<KeywordEntity> keywordEntityList = mongoTemplate.find(new Query(Criteria.where(MongoEntityConstants.KEYWORD_ID)), KeywordEntity.class, MongoEntityConstants.TBL_KEYWORD);
        return keywordEntityList;
    }

    @Override
    public List<KeywordDTO> getKeywordByIds(List<Long> ids) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        return mongoTemplate.find(new Query(Criteria.where(MongoEntityConstants.KEYWORD_ID).in(ids)), getEntityClass(), MongoEntityConstants.TBL_KEYWORD);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<KeywordDTO> findByNames(String[] query, boolean fullMatch, PaginationParam param, Map<String, Object> queryParams) {

        Query mongoQuery = new Query();

        Criteria criteria = null;
        if (fullMatch) {
            criteria = Criteria.where(MongoEntityConstants.NAME).in(query);
        } else {
            String prefix = ".*(";
            String suffix = ").*";
            String reg = "";
            for (String name : query) {
                reg = reg + name + "|";
            }
            reg = reg.substring(0, reg.length() - 1);

            criteria = Criteria.where(MongoEntityConstants.NAME).regex(prefix + reg + suffix);

        }

        if (queryParams != null && !queryParams.isEmpty() && queryParams.size() > 0) {
//            Criteria criteria = Criteria.where(NAME).regex(prefix + reg + suffix);
            for (Map.Entry<String, Object> entry : queryParams.entrySet()) {
                if ("matchType".equals(entry.getKey())) {
                    Integer matchType = Integer.valueOf(entry.getValue().toString());
                    if (matchType == 1) {
                        criteria.and("mt").is(1);
                    } else if (matchType == 2) {
                        criteria.and("mt").is(2).and("pt").is(3);
                    } else if (matchType == 3) {
                        criteria.and("mt").is(2).and("pt").is(2);
                    } else if (matchType == 4) {
                        criteria.and("mt").is(2).and("pt").is(1);
                    } else if (matchType == 5) {
                        criteria.and("mt").is(3);
                    }
                }

                if ("adgroupIds".equals(entry.getKey())) {
                    criteria.and(MongoEntityConstants.ADGROUP_ID).in((ArrayList<Long>) entry.getValue());
                }

                if ("status".equals(entry.getKey())) {
                    criteria.and("s").is((Integer) entry.getValue());
                }
            }
        }
        mongoQuery.addCriteria(criteria);

        return getMongoTemplate().find(param.withParam(mongoQuery), getEntityClass());
    }

    @Override
    public List<KeywordDTO> findByIds(List<Long> ids, PaginationParam... param) {
        if (param.length > 0) {
            return getMongoTemplate().find(param[0].withParam(Query.query(Criteria.where(MongoEntityConstants.KEYWORD_ID).in(ids))), getEntityClass());
        }
        return getMongoTemplate().find(Query.query(Criteria.where(MongoEntityConstants.KEYWORD_ID).in(ids)), getEntityClass());
    }

//    @Override
//    public Pager getKeywordByPager(HttpServletRequest request, Map<String, Object> params, int orderBy) {
//        int start = Integer.parseInt(request.getParameter(START));
//        int pageSize = Integer.parseInt(request.getParameter(PAGESIZE));
//        Pager pager = findByPager(start, pageSize, params, orderBy);
//        return pager;
//    }

    @Override
    public List<KeywordDTO> getKeywordInfo() {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        return mongoTemplate.findAll(KeywordDTO.class, "keywordInfo");
    }

    @Override
    public Long keywordCount(List<Long> adgroupIds) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        return mongoTemplate.count(Query.query(
                        Criteria.where(MongoEntityConstants.ACCOUNT_ID).is(AppContext.getAccountId()).and(MongoEntityConstants.ADGROUP_ID).in(adgroupIds)),
                getEntityClass());
    }

    @Override
    public void insertAndQuery(List<KeywordDTO> keywordDTOList) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        for (KeywordEntity key : keywordEntity) {
            Query q = new Query(Criteria.where(getId()).is(key.getKeywordId()));
            if (!mongoTemplate.exists(q, KeywordEntity.class)) {
                mongoTemplate.insert(key);
                DataOperationLogEntity log = LogUtils.getLog(key.getKeywordId(), KeywordEntity.class, null, key);
                logProcessingDAO.insert(log);
            }
        }
    }

    @Override
    public KeywordDTO findByName(String name, Long accountId) {
        List<KeywordEntity> list = findByQuery(Query.query(Criteria.where("kw").is(name).and(MongoEntityConstants.ACCOUNT_ID).is(accountId)));
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public KeywordDTO findOne(Long keywordId) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        KeywordEntity entity = mongoTemplate.
                findOne(new Query(Criteria.where(getId()).is(keywordId)), KeywordEntity.class, MongoEntityConstants.TBL_KEYWORD);
        return entity;
    }

    public List<KeywordDTO> findAll() {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        List<KeywordEntity> keywordEntityList = mongoTemplate.find(Query.query(Criteria.where(MongoEntityConstants.ACCOUNT_ID).is(AppContext.getAccountId())), getEntityClass());
        return keywordEntityList;
    }

    public List<KeywordEntity> find(Map<String, Object> params, int skip, int limit, String order) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Query query = new Query();
        if (params != null && params.size() > 0) {
            Criteria criteria = Criteria.where(getId()).ne(null);
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                criteria.and(entry.getKey()).is(entry.getValue());
            }
            query.addCriteria(criteria);
        }
        query.with(new PageRequest(skip, limit, new Sort(Sort.Direction.DESC, order)));
        List<KeywordEntity> list = mongoTemplate.find(query, KeywordEntity.class, MongoEntityConstants.TBL_KEYWORD);
        return list;
    }


    //x
    public List<KeywordDTO> findByQuery(Query query) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        return mongoTemplate.find(query, KeywordEntity.class);
    }

    @Override
    public List<KeywordDTO> findByAdgroupId(Long adgroupId, PaginationParam param, Map<String, Object> queryParams) {
        Query query = new Query();
        Criteria criteria = Criteria.where(MongoEntityConstants.ADGROUP_ID).is(adgroupId);
        if (queryParams != null && !queryParams.isEmpty()) {
            for (Map.Entry<String, Object> entry : queryParams.entrySet()) {
                if ("status".equals(entry.getKey())) {
                    criteria.and("s").is(entry.getValue());
                }
            }
        }

        if (param == null) {
            query.addCriteria(criteria);
            return getMongoTemplate().find(query, KeywordEntity.class);
        } else {
            query.addCriteria(criteria);
            return getMongoTemplate().find(param.withParam(query), KeywordEntity.class);
        }
    }

    /**
     * 根据mongoID查询
     *
     * @param adgroupId
     * @param param
     * @return
     */
    @Override
    public List<KeywordDTO> findByAdgroupId(String adgroupId, PaginationParam param) {
        return getMongoTemplate().find(param.withParam(Query.query(Criteria.where(MongoEntityConstants.OBJ_ADGROUP_ID).is(adgroupId))), getEntityClass());
    }

    @Override
    public List<KeywordDTO> findByAdgroupIds(List<Long> adgroupIds, PaginationParam param, Map<String, Object> queryParams) {

        Query query = new Query();
        Criteria criteria = Criteria.where(MongoEntityConstants.ADGROUP_ID).in(adgroupIds);
        if (queryParams != null && !queryParams.isEmpty()) {
            for (Map.Entry<String, Object> entry : queryParams.entrySet()) {
                if ("status".equals(entry.getKey())) {
                    criteria.and("s").is(entry.getValue());
                }
            }
        }

        if (param != null) {
            query.addCriteria(criteria);
            return getMongoTemplate().find(param.withParam(query), getEntityClass());
        } else {
            query.addCriteria(criteria);
            return getMongoTemplate().find(query, getEntityClass());
        }
    }

    @Override
    public KeywordDTO findByObjectId(String oid) {
        return getMongoTemplate().findOne(Query.query(Criteria.where(MongoEntityConstants.SYSTEM_ID).is(oid)), getEntityClass());
    }

    @Override
    public void updateAdgroupIdByOid(String id, Long adgroupId) {
        getMongoTemplate().updateMulti(Query.query(Criteria.where(MongoEntityConstants.OBJ_ADGROUP_ID).is(id)), Update.update(MongoEntityConstants.ADGROUP_ID, adgroupId).set(MongoEntityConstants.OBJ_ADGROUP_ID, null), getEntityClass());
    }

    public void insert(KeywordEntity keywordEntity) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        mongoTemplate.insert(keywordEntity, MongoEntityConstants.TBL_KEYWORD);
    }

    public void insertAll(List<KeywordDTO> entities) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        mongoTemplate.insertAll(entities);
    }

    @SuppressWarnings("unchecked")
    public void update(KeywordDTO keywordDTO) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Long id = keywordDTO.getKeywordId();
        Query query = new Query();
        query.addCriteria(Criteria.where(getId()).is(id));
        Update update = new Update();
        try {
            Class _class = keywordDTO.getClass();
            Field[] fields = _class.getDeclaredFields();//get object's fields by reflect
            for (Field field : fields) {
                String fieldName = field.getName();
                if ("keywordId".equals(fieldName))
                    continue;
                StringBuilder fieldGetterName = new StringBuilder("get");
                fieldGetterName.append(fieldName.substring(0, 1).toUpperCase()).append(fieldName.substring(1));
                Method method = _class.getDeclaredMethod(fieldGetterName.toString());
                if (method == null)
                    continue;

                Object after = method.invoke(keywordDTO);
                if (after != null) {
                    update.set(field.getName(), after);
                    Object before = method.invoke(findOne(id));
                    break;
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        mongoTemplate.updateFirst(query, update, KeywordEntity.class, MongoEntityConstants.TBL_KEYWORD);
    }


    //xj
    public void update(KeywordDTO keywordDTO, KeyWordBackUpDTO keyWordBackUpDTO) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Long id = keywordDTO.getKeywordId();
        Query query = new Query();
        query.addCriteria(Criteria.where(MongoEntityConstants.SYSTEM_ID).is(keywordDTO.getId()));
        Update update = new Update();
        try {
            Class _class = keywordDTO.getClass();
            Field[] fields = _class.getDeclaredFields();//get object's fields by reflect
            for (Field field : fields) {
                String fieldName = field.getName();
                if (MongoEntityConstants.SYSTEM_ID.equals(fieldName))
                    continue;
                StringBuilder fieldGetterName = new StringBuilder("get");
                fieldGetterName.append(fieldName.substring(0, 1).toUpperCase()).append(fieldName.substring(1));
                Method method = _class.getDeclaredMethod(fieldGetterName.toString());
                if (method == null)
                    continue;

                Object after = method.invoke(keywordDTO);
                if (after != null) {
                    update.set(field.getName(), after);
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        mongoTemplate.updateFirst(query, update, KeywordEntity.class, MongoEntityConstants.TBL_KEYWORD);
        KeyWordBackUpDTO _keyWordBackUpDTO = keyWordBackUpDAO.findByObjectId(keywordDTO.getId());
        if (_keyWordBackUpDTO==null && keywordDTO.getLocalStatus() == 2) {
            KeyWordBackUpEntity backUpEntity = new KeyWordBackUpEntity();
            BeanUtils.copyProperties(keyWordBackUpDTO, backUpEntity);
            getMongoTemplate().insert(backUpEntity);
        }
        logDao.insertLog(id, LogStatusConstant.ENTITY_KEYWORD, LogStatusConstant.OPT_UPDATE);
    }

    /**
     * 还原功能的软删除
     *
     * @param id
     */
    public void updateLocalstatu(long id) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Update update = new Update();
        update.set("ls", "");
        mongoTemplate.updateFirst(new Query(Criteria.where(MongoEntityConstants.KEYWORD_ID).is(id)), update, MongoEntityConstants.TBL_KEYWORD);
    }


    /**
     * xj
     * 根据推广单元的mongodb ID 删除该单元下的所有与关键词
     *
     * @param agids
     */
    public void deleteByObjectAdgroupIds(List<String> agids) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        mongoTemplate.remove(new Query(Criteria.where(MongoEntityConstants.OBJ_ADGROUP_ID).in(agids)), MongoEntityConstants.TBL_KEYWORD);
    }


    /**
     * xj
     * 根据推广单元Long id 软删除该单元下的所有关键词(实则是修改localStaut 为 3);
     *
     * @param longSet
     */
    public void softDeleteByLongAdgroupIds(List<Long> longSet) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Update update = new Update();
        update.set("ls", 3);
        mongoTemplate.updateMulti(new Query(Criteria.where(MongoEntityConstants.ADGROUP_ID).in(longSet)), update, MongoEntityConstants.TBL_KEYWORD);
    }


    /**
     * 根据关键词的多个mongdb id得到关键词
     */
    public List<KeywordDTO> findByObjectIds(List<String> strIds) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        return mongoTemplate.find(new Query(Criteria.where(MongoEntityConstants.SYSTEM_ID).in(strIds)), getEntityClass(), MongoEntityConstants.TBL_KEYWORD);
    }


    /**
     * 根据传过来的关键词的long id 查询
     * @param ids
     * @return
     */
    public  List<KeywordDTO> findKeywordByIds(List<Long> ids){
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        return mongoTemplate.find(new Query(Criteria.where(MongoEntityConstants.KEYWORD_ID).in(ids)),getEntityClass(),MongoEntityConstants.TBL_KEYWORD);
    }

    /**
     * 根据mongodbID修改
     *
     * @param keywordEntity
     */
    public void updateByMongoId(KeywordDTO keywordDTO) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        String id = keywordEntity.getId();
        Query query = new Query();
        query.addCriteria(Criteria.where(MongoEntityConstants.SYSTEM_ID).is(id));
        Update update = new Update();

        try {
            Class _class = keywordEntity.getClass();
            Field[] fields = _class.getDeclaredFields();//get object's fields by reflect
            for (Field field : fields) {
                String fieldName = field.getName();
                if ("id".equals(fieldName))
                    continue;
                StringBuilder fieldGetterName = new StringBuilder("get");
                fieldGetterName.append(fieldName.substring(0, 1).toUpperCase()).append(fieldName.substring(1));
                Method method = _class.getDeclaredMethod(fieldGetterName.toString());
                if (method == null)
                    continue;

                Object after = method.invoke(keywordEntity);
                if (after != null) {
                    update.set(field.getName(), after);
                    logDao.insertLog(id, LogStatusConstant.ENTITY_KEYWORD);
                    break;
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        mongoTemplate.updateFirst(query, update, KeywordEntity.class, MongoEntityConstants.TBL_KEYWORD);
    }


    public void update(List<KeywordEntity> entities) {
        for (KeywordEntity entity : entities)
            update(entity);
    }

    @SuppressWarnings("unchecked")
    public void updateMulti(String fieldName, String seedWord, Object value) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Class _class = KeywordEntity.class;
        Query query = new Query();
        query.addCriteria(Criteria.where(MongoEntityConstants.TBL_KEYWORD).
                regex(Pattern.compile("^.*?" + seedWord + ".*$", Pattern.CASE_INSENSITIVE)));
        List<KeywordEntity> keywordEntities = mongoTemplate.find(query, _class, MongoEntityConstants.TBL_KEYWORD);
        Update update = new Update();
        update.set(fieldName, value);
        mongoTemplate.updateMulti(query, update, MongoEntityConstants.TBL_KEYWORD);
        try {
            for (KeywordEntity entity : keywordEntities) {
                StringBuilder fieldGetterName = new StringBuilder("get");
                fieldGetterName.append(fieldName.substring(0, 1).toUpperCase()).append(fieldName.substring(1));
                Method method = _class.getDeclaredMethod(fieldGetterName.toString());
                Object before = method.invoke(entity);
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateMultiKeyword(Long[] ids, BigDecimal price, String pcUrl) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Query query = Query.query(Criteria.where(MongoEntityConstants.KEYWORD_ID).in(ids));
        Update update = new Update();

//        CommonService commonService = BaiduServiceSupport.getCommonService(accountManageDAO.findByBaiduUserId(AppContext.getAccountId()));
//        BaiduApiService apiService = new BaiduApiService(commonService);
//        List<KeywordEntity> keywordTypeList = new ArrayList<>(ids.length);
//
//        if (price != null) {
//            if (price.doubleValue() == 0) {
//                //使用单元出价
//                for (Long id : ids) {
//                    AdgroupEntity adgroupEntity = findByKeywordId(id);
//                    Double _price;
//                    if (adgroupEntity != null) {
//                        _price = adgroupEntity.getMaxPrice();
//                        BigDecimal adgroupPrice = new BigDecimal(_price);
//                        update.set("pr", adgroupPrice);
//                        mongoTemplate.updateMulti(query, update, getEntityClass());
//
//                        //baidu api
//                        KeywordEntity keywordType = new KeywordEntity();
//                        keywordType.setKeywordId(id);
//                        keywordType.setPrice(BigDecimal.valueOf(adgroupPrice.doubleValue()));
//                        keywordTypeList.add(keywordType);
//                    }
//                }
//            } else {
//                update.set("pr", price);
//                mongoTemplate.updateMulti(query, update, getEntityClass());
//
//                //baidu api
//                for (Long id : ids) {
//                    KeywordEntity keywordType = new KeywordEntity();
//                    keywordType.setKeywordId(id);
//                    keywordType.setPrice(BigDecimal.valueOf(price.doubleValue()));
//                    keywordTypeList.add(keywordType);
//                }
//            }
//        }
//        if (pcUrl != null) {
//            update.set("pc", pcUrl);
//            mongoTemplate.updateMulti(query, update, getEntityClass());
//
//            //baidu api
//            for (Long id : ids) {
//                KeywordEntity keywordType = new KeywordEntity();
//                keywordType.setKeywordId(id);
//                keywordType.setPcDestinationUrl(pcUrl);
//                keywordTypeList.add(keywordType);
//            }
//        }
//
//        try {
//            List<KeywordEntity> list = apiService.updateKeyword(keywordTypeList);
//            if (list.isEmpty()) {
//                throw new ApiException();
//            }
//        } catch (ApiException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public AdgroupEntity findByKeywordId(Long keywordId) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        KeywordEntity keywordEntity = mongoTemplate.findOne(Query.query(Criteria.where(MongoEntityConstants.KEYWORD_ID).is(keywordId)), getEntityClass());
        Long adgroupId = keywordEntity.getAdgroupId();
        return mongoTemplate.findOne(Query.query(Criteria.where(MongoEntityConstants.ADGROUP_ID).is(adgroupId)), AdgroupEntity.class);
    }

    public void deleteById(Long id) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        mongoTemplate.remove(new Query(Criteria.where(MongoEntityConstants.KEYWORD_ID).is(id)), KeywordEntity.class, MongoEntityConstants.TBL_KEYWORD);
    }

    /**
     * 根据mongoId硬删除
     *
     * @param id
     */
    public void deleteById(String id) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        mongoTemplate.remove(new Query(Criteria.where(MongoEntityConstants.SYSTEM_ID).is(id)), KeywordEntity.class, MongoEntityConstants.TBL_KEYWORD);
        logDao.insertLog(id, LogStatusConstant.ENTITY_KEYWORD);
    }

    /**
     * 根据Long类型id软删除
     *
     * @param id
     */
    public void softDelete(Long id) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Update update = new Update();
        update.set("ls", 3);
        mongoTemplate.updateFirst(new Query(Criteria.where(MongoEntityConstants.KEYWORD_ID).is(id)), update, MongoEntityConstants.TBL_KEYWORD);
    }


    @Override
    public void deleteByIds(List<Long> ids) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        for (Long id : ids) {
            mongoTemplate.remove(new Query(Criteria.where(MongoEntityConstants.KEYWORD_ID).is(id)), KeywordEntity.class, MongoEntityConstants.TBL_KEYWORD);
        }
    }

    @Override
    public Class<KeywordDTO> getEntityClass() {
        return KeywordDTO.class;
    }

    public void delete(KeywordEntity keywordEntity) {
        deleteById(keywordEntity.getKeywordId());
    }


    public void deleteAll() {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        List<KeywordEntity> keywordEntities = findAll();
        getMongoTemplate().dropCollection(KeywordEntity.class);
    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> params, int orderBy) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Query q = new Query();
        List<KeywordEntity> list;
        if (params != null && params.size() > 0) {
            q.skip(start);
            q.limit(pageSize);
            Criteria where = Criteria.where(MongoEntityConstants.KEYWORD_ID).ne(null);
            for (Map.Entry<String, Object> m : params.entrySet()) {
                where.and(m.getKey()).is(m.getValue());
            }
            q.addCriteria(where);
        }
        addOrder(orderBy, q);
        list = mongoTemplate.find(q, KeywordEntity.class, MongoEntityConstants.TBL_KEYWORD);
        Pager p = new Pager();
        p.setRows(list);

        return p;
    }


    //xj
    @Override
    public PagerInfo findByPageInfo(Query q, int pageSize, int pageNo) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        int totalCount = getListTotalCount(q);
        PagerInfo p = new PagerInfo(pageNo, pageSize, totalCount);
        q.skip(p.getFirstStation());
        q.limit(p.getPageSize());
//        q.with(new Sort(Sort.Direction.DESC, "name"));
        if (totalCount < 1) {
            p.setList(new ArrayList());
            return p;
        }
        List list = mongoTemplate.find(q, getEntityClass());
        p.setList(list);
        return p;
    }

    //xj
    public int getListTotalCount(Query q) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        return (int) mongoTemplate.count(q, MongoEntityConstants.TBL_KEYWORD);
    }


    private int getCount(Map<String, Object> params, String collections, String nell) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Query q = new Query();
        if (params != null && params.size() > 0) {
            Criteria where = nell != null ? Criteria.where(nell).ne(null) : null;
            for (Map.Entry<String, Object> m : params.entrySet()) {
                where.and(m.getKey()).is(m.getValue());
            }
            q.addCriteria(where);
        }
        return (int) mongoTemplate.count(q, collections);
    }

    private void addOrder(int orderBy, Query q) {
        switch (orderBy) {
            case 1:
                q.with(new Sort(Sort.Direction.DESC, "price"));
                break;
            default:
                q.with(new Sort(Sort.Direction.DESC, "price"));
                break;
        }
    }


    public void remove(Query query) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        mongoTemplate.remove(query, KeywordEntity.class, MongoEntityConstants.TBL_KEYWORD);
    }

}
