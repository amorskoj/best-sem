package com.perfect.mongodb.dao.impl;

import com.perfect.entity.CampaignEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by vbzer_000 on 2014/6/27.
 */
@Repository(value = "campaignDAO")
public class CampaignDAOImpl extends AbstractBaseDAOImpl<CampaignEntity> implements com.perfect.dao.CampaignDAO {
    @Override
    public void deleteAll() {
        getMongoTemplate().dropCollection(CampaignEntity.class);
    }

    @Override
    public void updateById(CampaignEntity campaignEntity) {

    }

    @Override
    public void update(CampaignEntity s, CampaignEntity d) {

    }

    @Override
    public CampaignEntity findById(String id) {
        return null;
    }

    @Override
    public List<CampaignEntity> findAll() {
        return null;
    }

    @Override
    public List<CampaignEntity> find(CampaignEntity campaignEntity, int skip, int limit) {
        return null;
    }

    @Override
    public CampaignEntity findAndModify(CampaignEntity q, CampaignEntity u) {
        return null;
    }

    @Override
    public CampaignEntity findAndRemove(CampaignEntity campaignEntity) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }
}
