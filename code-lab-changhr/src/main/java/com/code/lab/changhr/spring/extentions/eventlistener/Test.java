package com.code.lab.changhr.spring.extentions.eventlistener;

import com.code.lab.changhr.spring.extentions.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 测试简单的事件监听
 *
 * @author changhr
 * @create 2020-03-30 13:16
 */
public class Test {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        TestEvent testEvent = new TestEvent("hello", "message");
        context.publishEvent(testEvent);
    }

}
