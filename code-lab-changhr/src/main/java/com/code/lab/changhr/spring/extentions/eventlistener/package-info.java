package com.code.lab.changhr.spring.extentions.eventlistener;

/*
 * 本示例演示 Spring 的事件监听的简单用法
 *
 *
 * Spring 事件传播器
 *
 * ApplicationEventMulticaster 初始化：
 * 1. 如果用户自定义了事件广播器，那么使用用户自定义的事件广播器
 * 2. 如果用户没有自定义事件广播器，那么使用默认的 ApplicationEventMulticaster
 *    默认的广播器实现：SimpleApplicationEventMulticaster
 *
 * 当产生 Spring 事件的时候会默认使用 SimpleApplicationEventMulticaster 的multicastEvent 来广播事件，
 * 遍历所有监听器，并使用监听器中的 onApplicationEvent 方法来进行监听器的处理。
 *
 * 而对于每个监听器来说其实都可以获取到产生的事件，但是是否进行处理则由事件监听器来决定。
 *
 */