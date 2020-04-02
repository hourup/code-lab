package com.code.lab.changhr.concurrency.java8.future.simulation;

/**
 * @author changhr
 * @create 2020-04-02 14:51
 */
public interface SimpleFuture<T> {

    /**
     * 返回计算的结果，该方法会陷入阻塞状态
     */
    T get() throws InterruptedException;

    /**
     * 判断任务是否已经被执行完成
     */
    boolean done();
}
