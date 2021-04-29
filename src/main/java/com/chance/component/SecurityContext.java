package com.chance.component;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Description: SecurityContext
 * @Author: chance
 * @Date: 4/23/21 10:37 AM
 * @Version 1.0
 */
@Component
public class SecurityContext implements ApplicationContextAware {

    ApplicationContext applicationContext;

    public SecurityContext() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
