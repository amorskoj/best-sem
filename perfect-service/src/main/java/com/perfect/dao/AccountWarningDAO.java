package com.perfect.dao;

import com.perfect.entity.WarningRuleEntity;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by john on 2014/8/5.
 */
public interface AccountWarningDAO extends CrudRepository<WarningRuleEntity, Long> {
    List<WarningRuleEntity> findEnableIsOne();

    void update(WarningRuleEntity warningRuleEntity);

    void updateMulti(Query query, Update update);

    List<WarningRuleEntity> findWarningRule(int isEnable, int isWarninged);

    Iterable<WarningRuleEntity> findByUserName(String user);
}
