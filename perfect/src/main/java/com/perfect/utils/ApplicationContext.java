package com.perfect.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;

/**
 * Created by baizz on 2014-6-10.
 */
@Service("com.perfect.util.applicationContext")
public class ApplicationContext implements ApplicationContextAware {

    private static org.springframework.context.ApplicationContext currentApplicationContext = null;

    public static org.springframework.context.ApplicationContext getCurrentApplicationContext() {
        if (currentApplicationContext == null) {
            currentApplicationContext = ContextLoader.getCurrentWebApplicationContext();
            if (currentApplicationContext == null)
                currentApplicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        }
        return currentApplicationContext;
    }

    @Override
    public void setApplicationContext(org.springframework.context.ApplicationContext applicationContext) throws BeansException {
        currentApplicationContext = applicationContext;
    }
}
