package com.stulsoft.pspring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.when;

/**
 * @author Yuriy Stul.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/test3.conf.xml"})
public class Manager3Test implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Test
    public void userService() {
        IService3 service3Mock = applicationContext.getBean("service3", IService3.class);
        when(service3Mock.getNextInt()).thenReturn(123);

        Manager3 manager3 = applicationContext.getBean(Manager3.class);
        manager3.userService();

        when(service3Mock.getNextInt()).thenReturn(456);
        manager3.userService();
    }
}