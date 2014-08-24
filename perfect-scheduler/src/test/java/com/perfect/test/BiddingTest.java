package com.perfect.test;

import com.perfect.constants.BiddingStrategyConstants;
import com.perfect.core.AppContext;
import com.perfect.dao.BiddingRuleDAO;
import com.perfect.entity.bidding.BiddingRuleEntity;
import com.perfect.entity.bidding.StrategyEntity;
import com.perfect.schedule.task.execute.BiddingTask;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.unitils.UnitilsJUnit4;

import java.util.List;

/**
 * Created by yousheng on 2014/8/20.
 *
 * @author yousheng
 */
public class BiddingTest extends UnitilsJUnit4 {


    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("schedule.xml");
        BiddingTask biddingTask = (BiddingTask) applicationContext.getBean("biddingTask");

        AppContext.setUser("perfect");



//        createEntity(applicationContext);
        List<BiddingTask.TaskObject> list = null;
        try {
            list = biddingTask.selectTasks(null, null, 0, null, 0);
            biddingTask.execute(list.toArray(new BiddingTask.TaskObject[]{}), null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void createEntity(ClassPathXmlApplicationContext applicationContext) {
        BiddingRuleDAO biddingRuleDAO = (BiddingRuleDAO) applicationContext.getBean("biddingRuleDAO");

        BiddingRuleEntity biddingRuleEntity = new BiddingRuleEntity();
        biddingRuleEntity.setAccountId(7001963);
        biddingRuleEntity.setNextTime(1);
        biddingRuleEntity.setKeywordId(906068486l);
        biddingRuleEntity.setKeyword("深圳南山 买房");
        biddingRuleEntity.setEnabled(true);
        biddingRuleEntity.setCurrentPrice(1);

        StrategyEntity strategyEntity = new StrategyEntity();
        biddingRuleEntity.setStrategyEntity(strategyEntity);
        strategyEntity.setInterval(30);
        strategyEntity.setSpd(BiddingStrategyConstants.SPD_FAST.value());
        strategyEntity.setMaxPrice(5);
        strategyEntity.setMinPrice(1);

        strategyEntity.setPositionStrategy(BiddingStrategyConstants.POS_LEFT_1.value());

        biddingRuleDAO.createBidding(biddingRuleEntity);
    }

    public void test() {

        try {
            BiddingTask biddingTask = new BiddingTask();
            List<BiddingTask.TaskObject> list = biddingTask.selectTasks(null, null, 0, null, 0);

            biddingTask.execute(list.toArray(new BiddingTask.TaskObject[]{}), null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
