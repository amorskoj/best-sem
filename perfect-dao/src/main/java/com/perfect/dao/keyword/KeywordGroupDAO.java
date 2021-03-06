package com.perfect.dao.keyword;

import com.perfect.dao.base.HeyCrudRepository;
import com.perfect.dto.keyword.LexiconDTO;
import com.perfect.utils.paging.PagerInfo;

import java.util.List;
import java.util.Map;

/**
 * Created on 2014-08-21.
 *
 * @author dolphineor
 * @update 2015-09-28
 */
public interface KeywordGroupDAO extends HeyCrudRepository<LexiconDTO, String> {

    String LEXICON_TRADE = "tr";

    String LEXICON_CATEGORY = "cg";

    String LEXICON_GROUP = "gr";

    String LEXICON_KEYWORD = "kw";

    String LEXICON_URL = "url";

    /**
     * 查询行业词库下的类别
     *
     * @param trade
     * @return
     */
    List<?> findCategories(String trade);

    /**
     * <p>查询行业词库下的二级目录.
     *
     * @param categories
     * @return
     */
    List<?> findSecondDirectoryByCategories(List<String> categories);

    /**
     * 加载行业库
     *
     * @return
     */
    List<?> findTr();

    /**
     * 添加行业库数据
     *
     * @param lexiconDTO
     */
    int saveTrade(LexiconDTO lexiconDTO);

    /**
     * 获取当前结果集的长度
     *
     * @param params
     * @return
     */
    long getCurrentRowsSize(Map<String, Object> params);

    /**
     * 分页查询行业库
     *
     * @param params 查询参数
     * @param page
     * @param limit
     * @return
     */
    PagerInfo findByPager(Map<String, Object> params, int page, int limit);

    /**
     * 根据行业，关键词删除一条数据
     *
     * @param trade
     * @param keyword
     */
    void deleteByParams(String trade, String keyword);

    /**
     * 根据一些参数修改
     *
     * @param mapParams
     */
    void updateByParams(Map<String, Object> mapParams);
}
