package com.code.lab.changhr.spring.extentions.aware;

import com.code.lab.changhr.spring.extentions.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 测试方法
 *
 * @author changhr
 * @create 2020-03-30 10:58
 */
public class Test {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        SimpleAwareBean instance = (SimpleAwareBean) context.getBean("awareTest");
        System.out.println(instance);
        context.destroy();
    }
}
