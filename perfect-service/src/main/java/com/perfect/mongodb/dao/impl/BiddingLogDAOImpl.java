package com.perfect.mongodb.dao.impl;

import com.perfect.dao.BiddingLogDAO;
import com.perfect.entity.bidding.BiddingLogEntity;
import com.perfect.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.mongodb.utils.Pager;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by Administrator on 14-9-4.
 */
@Component
public class BiddingLogDAOImpl extends AbstractUserBaseDAOImpl<BiddingLogEntity, Long> implements BiddingLogDAO {
    @Override
    public Class<BiddingLogEntity> getEntityClass() {
        return BiddingLogEntity.class;
    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }
}
