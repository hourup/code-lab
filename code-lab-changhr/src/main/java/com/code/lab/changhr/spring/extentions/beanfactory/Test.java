package com.code.lab.changhr.spring.extentions.beanfactory;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author changhr
 * @create 2019-12-09 10:12
 */
public class Test {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/beanFactory/BeanFactory.xml");
        BeanFactoryPostProcessor bfpp = (BeanFactoryPostProcessor) context.getBean("bfpp");
        System.out.println(bfpp);
        System.out.println(bfpp.getClass());
        System.out.println(context.getBean("simpleBean"));
    }
}
