package com.code.lab.changhr.spring.extentions.eventlistener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.stereotype.Component;

/**
 * 定义监听器
 *
 * @author changhr
 * @create 2020-03-30 13:14
 */
@Component
public class TestListener implements ApplicationListener<ApplicationEvent> {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof TestEvent) {
            TestEvent testEvent = (TestEvent) event;
            System.out.println(testEvent.getSource().toString());
            testEvent.print();
        }
    }
}
