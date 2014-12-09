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
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by baizz on 2014-08-07.
 * 2014-11-26 refactor
 */
@Repository("asynchronousReportDAO")
public class AsynchronousReportDAOImpl extends AbstractUserBaseDAOImpl<AccountReportDTO,Long> implements AsynchronousReportDAO {

    @Override
    public void getAccountReportData(List<AccountReportDTO> accountReportDTOs, SystemUserDTO systemUser, String dateStr) {
        MongoTemplate mongoTemplate;
        mongoTemplate = BaseMongoTemplate.getMongoTemplate(DBNameUtils.getReportDBName(systemUser.getUserName()));

        List<AccountReportEntity> accountReportEntities = ObjectUtils.convert(accountReportDTOs, AccountReportEntity.class);
        mongoTemplate.insert(accountReportEntities, TBL_ACCOUNT_REPORT);
    }

    @Override
    public void getCampaignReportData(List<CampaignReportDTO> campaignReportDTOs, SystemUserDTO systemUser, String dateStr) {
        MongoTemplate mongoTemplate;
        mongoTemplate = BaseMongoTemplate.getMongoTemplate(DBNameUtils.getReportDBName(systemUser.getUserName()));

        List<CampaignReportEntity> campaignReportEntities = ObjectUtils.convert(campaignReportDTOs, CampaignReportEntity.class);
        mongoTemplate.insert(campaignReportEntities, dateStr + "-campaign");
    }

    @Override
    public void getAdgroupReportData(List<AdgroupReportDTO> adgroupReportDTOs, SystemUserDTO systemUser, String dateStr) {
        MongoTemplate mongoTemplate;
        mongoTemplate = BaseMongoTemplate.getMongoTemplate(DBNameUtils.getReportDBName(systemUser.getUserName()));

        List<AdgroupReportEntity> adgroupReportEntities = ObjectUtils.convert(adgroupReportDTOs, AdgroupReportEntity.class);
        mongoTemplate.insert(adgroupReportEntities, dateStr + "-adgroup");
    }

    @Override
    public void getCreativeReportData(List<CreativeReportDTO> creativeReportDTOs, SystemUserDTO systemUser, String dateStr) {
        MongoTemplate mongoTemplate;
        mongoTemplate = BaseMongoTemplate.getMongoTemplate(DBNameUtils.getReportDBName(systemUser.getUserName()));

        List<CreativeReportEntity> creativeReportEntities = ObjectUtils.convert(creativeReportDTOs, CreativeReportEntity.class);
        mongoTemplate.insert(creativeReportEntities, dateStr + "-creative");
    }

    @Override
    public void getKeywordReportData(List<KeywordReportDTO> keywordReportDTOs, SystemUserDTO systemUser, String dateStr) {
        MongoTemplate mongoTemplate;
        mongoTemplate = BaseMongoTemplate.getMongoTemplate(DBNameUtils.getReportDBName(systemUser.getUserName()));

        List<KeywordReportEntity> keywordReportEntities = ObjectUtils.convert(keywordReportDTOs, KeywordReportEntity.class);
        mongoTemplate.insert(keywordReportEntities, dateStr + "-keyword");
    }

    @Override
    public void getRegionReportData(List<RegionReportDTO> regionReportDTOs, SystemUserDTO systemUser, String dateStr) {
        MongoTemplate mongoTemplate;
        mongoTemplate = BaseMongoTemplate.getMongoTemplate(DBNameUtils.getReportDBName(systemUser.getUserName()));

        List<RegionReportEntity> regionReportEntities = ObjectUtils.convert(regionReportDTOs, RegionReportEntity.class);
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