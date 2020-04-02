package com.code.lab.changhr.concurrency.java8.future.simulation;

/**
 * @author changhr
 * @create 2020-04-02 14:58
 */
@FunctionalInterface
public interface Task<IN, OUT> {

    /**
     * 给定一个参数，经过计算返回结果
     */
    OUT get(IN input);
}
