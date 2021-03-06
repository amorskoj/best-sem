package com.perfect.service;

import com.perfect.dto.bidding.BiddingRuleDTO;
import com.perfect.param.BiddingRuleParam;
import com.perfect.utils.paging.PaginationParam;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by yousheng on 2014/7/30.
 *
 * @author yousheng
 */
public interface BiddingRuleService {

    void createBiddingRule(BiddingRuleDTO biddingRuleEntity);

    BiddingRuleDTO findByKeywordId(Long keywordId);

    void updateToNextTime(BiddingRuleDTO biddingRuleEntity, int time);

    void createRule(BiddingRuleDTO biddingRuleDTO);

    void disableRule(Long keywordId);

    void enableRule(Long keywordId);

    boolean isPause(Long accountId, Long keywordId);

    void save(BiddingRuleDTO biddingRuleDTO);

    void updateRule(BiddingRuleParam param);

    List<BiddingRuleDTO> getTaskByAccountId(String userName, Long id, long hour);

    void updateRule(List<BiddingRuleDTO> tasks);

    List<BiddingRuleDTO> findRules(Map<String, Object> q, int skip, int limit, String sort, boolean asc);

    List<BiddingRuleDTO> findRules(Map<String, Object> q, String kw, String query, int skip, int limit, String sort, boolean asc);

    List<BiddingRuleDTO> findByKeywordIds(List<Long> ids);

    void remove(Long id);

    void removeByKeywordId(Long id);

    void removeByKeywordIds(List<Long> id);

    boolean exists(Long keywordId);

    @Deprecated
    void updateRank(Collection<BiddingRuleDTO> values);

    boolean setEnable(Long[] ids, boolean ebl);

    List<BiddingRuleDTO> findByNames(String[] split, boolean fullMatch, PaginationParam param, Map<String, Object> queryParams);

    Integer countBiddingRuleDTOfindByNames(String[] split, boolean fullMatch, PaginationParam param, Map<String, Object> queryParams);

    BiddingRuleDTO takeOne(String userName, Long id, long time);

    List<BiddingRuleDTO> getAvailableRules(String username, long time);

    BiddingRuleDTO takeOneById(String username, String objectId);

    BiddingRuleDTO saveWithAccountId(BiddingRuleDTO biddingRuleDTO);

}
