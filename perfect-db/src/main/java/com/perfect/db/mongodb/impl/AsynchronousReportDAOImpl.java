package com.perfect.db.mongodb.impl;

import com.perfect.dao.report.AsynchronousReportDAO;
import com.perfect.db.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.db.mongodb.base.BaseMongoTemplate;
import com.perfect.dto.SystemUserDTO;
import com.perfect.dto.account.*;
import com.perfect.dto.keyword.KeywordReportDTO;
import com.perfect.entity.account.AccountReportEntity;
import com.perfect.entity.report.*;
import com.perfect.utils.ObjectUtils;
import com.perfect.utils.mongodb.DBNameUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by baizz on 2014-08-07.
 * 2014-11-26 refactor
 */
@Repository("asynchronousReportDAO")
public class AsynchronousReportDAOImpl extends AbstractUserBaseDAOImpl<AccountReportDTO, Long> implements AsynchronousReportDAO {

    @Override
    public void getAccountReportData(List<AccountReportDTO> accountReportDTOs, SystemUserDTO systemUser, String dateStr, String baiduUserName) {
        MongoTemplate mongoTemplate;
        mongoTemplate = BaseMongoTemplate.getMongoTemplate(DBNameUtils.getReportDBName(systemUser.getUserName()));

        List<AccountReportEntity> accountReportEntities = ObjectUtils.convert(accountReportDTOs, AccountReportEntity.class);
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = df.format(date);

        List<AccountReportEntity> entities = null;
        if (!dateStr.equals(dateString)) {
            try {
                entities = mongoTemplate.find(Query.query(Criteria.where("date").is(new SimpleDateFormat("yyyy-MM-dd").parse(dateStr)).and("acna").is(baiduUserName)), AccountReportEntity.class);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (entities == null || entities.size() == 0) {
                mongoTemplate.insert(accountReportEntities, TBL_ACCOUNT_REPORT);
            }
        }
    }

    @Override
    public void getCampaignReportData(List<CampaignReportDTO> campaignReportDTOs, SystemUserDTO systemUser, String dateStr, int i) {
        MongoTemplate mongoTemplate;
        mongoTemplate = BaseMongoTemplate.getMongoTemplate(DBNameUtils.getReportDBName(systemUser.getUserName()));

        List<CampaignReportEntity> campaignReportEntities = new ArrayList<>(ObjectUtils.convert(campaignReportDTOs, CampaignReportEntity.class));
        List<CampaignReportEntity> campaignReportEntities1;
        if (mongoTemplate.collectionExists(dateStr + "-campaign")) {
            if (i > 1) {
                campaignReportEntities1 = new ArrayList<>(mongoTemplate.find(new Query(), CampaignReportEntity.class, dateStr + "-campaign"));
                campaignReportEntities.addAll(campaignReportEntities1);
            }
            mongoTemplate.dropCollection(dateStr + "-campaign");
        }

        mongoTemplate.insert(campaignReportEntities, dateStr + "-campaign");
    }

    @Override
    public void getAdgroupReportData(List<AdgroupReportDTO> adgroupReportDTOs, SystemUserDTO systemUser, String dateStr, int i) {
        MongoTemplate mongoTemplate;
        mongoTemplate = BaseMongoTemplate.getMongoTemplate(DBNameUtils.getReportDBName(systemUser.getUserName()));

        List<AdgroupReportEntity> adgroupReportEntities = new ArrayList<>(ObjectUtils.convert(adgroupReportDTOs, AdgroupReportEntity.class));
        List<AdgroupReportEntity> adgroupReportEntities1;
        if (mongoTemplate.collectionExists(dateStr + "-adgroup")) {
            if (i > 1) {
                adgroupReportEntities1 = new ArrayList<>(mongoTemplate.find(new Query(), AdgroupReportEntity.class, dateStr + "-adgroup"));
                adgroupReportEntities.addAll(adgroupReportEntities1);
            }
            mongoTemplate.dropCollection(dateStr + "-adgroup");
        }

        mongoTemplate.insert(adgroupReportEntities, dateStr + "-adgroup");
    }

    @Override
    public void getCreativeReportData(List<CreativeReportDTO> creativeReportDTOs, SystemUserDTO systemUser, String dateStr, int i) {
        MongoTemplate mongoTemplate;
        mongoTemplate = BaseMongoTemplate.getMongoTemplate(DBNameUtils.getReportDBName(systemUser.getUserName()));

        List<CreativeReportEntity> creativeReportEntities = new ArrayList<>(ObjectUtils.convert(creativeReportDTOs, CreativeReportEntity.class));
        List<CreativeReportEntity> creativeReportEntities1;
        if (mongoTemplate.collectionExists(dateStr + "-creative")) {
            if (i > 1) {
                creativeReportEntities1 = new ArrayList<>(mongoTemplate.find(new Query(), CreativeReportEntity.class, dateStr + "-creative"));
                creativeReportEntities.addAll(creativeReportEntities1);
            }
            mongoTemplate.dropCollection(dateStr + "-creative");
        }

        mongoTemplate.insert(creativeReportEntities, dateStr + "-creative");
    }

    @Override
    public void getKeywordReportData(List<KeywordReportDTO> keywordReportDTOs, SystemUserDTO systemUser, String dateStr, int i) {
        MongoTemplate mongoTemplate;
        mongoTemplate = BaseMongoTemplate.getMongoTemplate(DBNameUtils.getReportDBName(systemUser.getUserName()));

        List<KeywordReportEntity> keywordReportEntities = new ArrayList<>(ObjectUtils.convert(keywordReportDTOs, KeywordReportEntity.class));
        List<KeywordReportEntity> keywordReportEntities1;
        if (mongoTemplate.collectionExists(dateStr + "-keyword")) {
            if (i > 1) {
                keywordReportEntities1 = new ArrayList<>(mongoTemplate.find(new Query(), KeywordReportEntity.class, dateStr + "-keyword"));
                keywordReportEntities.addAll(keywordReportEntities1);
            }
            mongoTemplate.dropCollection(dateStr + "-keyword");
        }

        mongoTemplate.insert(keywordReportEntities, dateStr + "-keyword");
    }

    @Override
    public void getRegionReportData(List<RegionReportDTO> regionReportDTOs, SystemUserDTO systemUser, String dateStr, int i) {
        MongoTemplate mongoTemplate;
        mongoTemplate = BaseMongoTemplate.getMongoTemplate(DBNameUtils.getReportDBName(systemUser.getUserName()));

        List<RegionReportEntity> regionReportEntities = new ArrayList<>(ObjectUtils.convert(regionReportDTOs, RegionReportEntity.class));
        List<RegionReportEntity> regionReportEntities1;
        if (mongoTemplate.collectionExists(dateStr + "-region")) {
            if (i > 1) {
                regionReportEntities1 = new ArrayList<>(mongoTemplate.find(new Query(), RegionReportEntity.class, dateStr + "-region"));
                regionReportEntities.addAll(regionReportEntities1);
            }
            mongoTemplate.dropCollection(dateStr + "-region");
        }

        mongoTemplate.insert(regionReportEntities, dateStr + "-region");
    }


    @Override
    public Class<AccountReportEntity> getEntityClass() {
        return AccountReportEntity.class;
    }

    @Override
    public Class<AccountReportDTO> getDTOClass() {
        return AccountReportDTO.class;
    }
}