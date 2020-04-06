package com.code.lab.changhr.concurrency.java8.lock;

/*
* ReentrantLock 与 锁
*
* ReentrantLock 独有的功能
* 1. 可指定是公平锁还是非公平锁
* 2. 提供了一个 Condition 类，可以分组唤醒需要唤醒的线程
* 3. 提供能够中断等待锁的线程的机制，lock.lockInterruptibly()
*
* tryLock: 仅在调用时锁定未被另一个线程保持的情况下，才能获取锁定
*
* */