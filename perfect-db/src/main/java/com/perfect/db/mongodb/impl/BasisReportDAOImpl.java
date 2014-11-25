package com.perfect.db.mongodb.impl;

import com.perfect.core.AppContext;
import com.perfect.dao.BasisReportDAO;
import com.perfect.db.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.db.mongodb.base.BaseMongoTemplate;
import com.perfect.db.mongodb.utils.DateUtils;
import com.perfect.dao.utils.Pager;
import com.perfect.dto.account.AccountReportDTO;
import com.perfect.entity.StructureReportEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.perfect.commons.constants.MongoEntityConstants.*;
import static com.perfect.db.mongodb.utils.DateUtils.KEY_DATE;

/**
 * Created by SubDong on 2014/8/6.
 */
@Repository("basisReportDAO")

public class BasisReportDAOImpl extends AbstractUserBaseDAOImpl<StructureReportEntity, Long> implements BasisReportDAO {
    @Override
    public List<StructureReportEntity> getUnitReportDate(String userTable, Long dataId, String dataName) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserReportMongo();
        if (dataId != 0 && !dataName.equals("0")) {
            List<StructureReportEntity> objectList = mongoTemplate.find(Query.query(Criteria.where(dataName).is(dataId).and(ACCOUNT_ID).is(AppContext.getAccountId())), StructureReportEntity.class, userTable);
            return objectList;
        }
        List<StructureReportEntity> objectList = mongoTemplate.find(Query.query(Criteria.where(ACCOUNT_ID).is(AppContext.getAccountId())), StructureReportEntity.class, userTable);
        return objectList;
    }

    @Override
    public List<AccountReportDTO> getAccountReport(int Sorted, String fieldName) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserReportMongo();
        Sort sort = null;
        if (Sorted == 0) {
            sort = new Sort(Sort.Direction.ASC, fieldName);
        } else {
            sort = new Sort(Sort.Direction.DESC, fieldName);
        }
        List<Date> dates = DateUtils.getsLatestAnyDays("yyyy-MM-dd", 2).get(KEY_DATE);
        List<AccountReportDTO> reportEntities = mongoTemplate.find(Query.query(Criteria.where("date").lte(dates.get(0)).gte(dates.get(1)).and(ACCOUNT_ID).is(AppContext.getAccountId())).with(sort), AccountReportDTO.class, TBL_ACCOUNT_REPORT);
        return reportEntities;
    }

    @Override
    public long getAccountCount() {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserReportMongo();
        long account_report = mongoTemplate.count(Query.query(Criteria.where("acid").is(AppContext.getAccountId())), TBL_ACCOUNT_REPORT);
        return account_report;
    }

    @Override
    public List<AccountReportDTO> getAccountReport(Date startDate, Date endDate) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserReportMongo();
        List<AccountReportDTO> reportResponses = mongoTemplate.find(Query.query(Criteria.where("date").gte(startDate).lte(endDate).and(ACCOUNT_ID).is(AppContext.getAccountId())), AccountReportDTO.class, TBL_ACCOUNT_REPORT);
        return reportResponses;
    }

    @Override
    public List<StructureReportEntity> getKeywordReport(Long[] id, String table) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserReportMongo();
        List<StructureReportEntity> entityList = mongoTemplate.find(new Query(Criteria.where(KEYWORD_ID).in(id).and(ACCOUNT_ID).is(AppContext.getAccountId())), StructureReportEntity.class, table);
        return entityList;
    }

    @Override
    public Class<StructureReportEntity> getEntityClass() {
        return StructureReportEntity.class;
    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }
}
