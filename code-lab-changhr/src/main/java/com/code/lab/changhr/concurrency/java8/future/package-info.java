package com.code.lab.changhr.concurrency.java8.future;
/*
* FutureTask
* https://img01.sogoucdn.com/app/a/100520146/e90b48afc88d698903f94e0ac064d06e
*
* 1. FutureTask 实现 Runnable 和 Future 接口；
* 2. 在线程上运行 FutureTask 后，run 方法被调用，run 方法会调用传入的 Callable 接口的 call 方法；
* 3. 拿到返回值后，通过 set 方法保存结果到 outcome，并且唤醒所有等待的线程；
* 4. 调用 get 方法获取执行结果时，如果没有执行完毕，则进入等待，直到 set 方法调用后被唤醒。
*
*/