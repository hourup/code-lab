package com.code.lab.changhr.spring.extentions.aware;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Aware 的使用
 * 可以在 Bean 中获取到 ApplicationContext 和 beanFactory
 *
 * @author changhr
 * @create 2019-11-25 17:31
 */
@Component
public class SimpleAwareBean implements BeanNameAware, ApplicationContextAware, BeanFactoryAware,
        InitializingBean, DisposableBean {

    private BeanFactory beanFactory;

    private ApplicationContext applicationContext;

    @Override
    public void setBeanName(String beanName) {
        System.out.println("[AwareTest][BeanNameAware][setBeanName] " + beanName);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
        System.out.println("[AwareTest][BeanFactoryAware][setBeanFactory] " + beanFactory);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        System.out.println("[AwareTest][ApplicationContextAware][setApplicationContext] " + applicationContext);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("[AwareTest][InitializingBean][afterPropertiesSet]");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("[AwareTest][DisposableBean][destroy]");
    }
}
