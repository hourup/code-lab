package com.code.lab.changhr.spring.extentions.eventlistener;

import org.springframework.context.ApplicationEvent;

/**
 * 定义监听事件
 *
 * @author changhr
 * @create 2020-03-30 13:13
 */
public class TestEvent extends ApplicationEvent {

    private String msg;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public TestEvent(Object source) {
        super(source);
    }

    public TestEvent(Object source, String msg) {
        super(source);
        this.msg = msg;
    }

    public void print() {
        System.out.println(msg);
    }
}
