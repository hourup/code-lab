package com.code.lab.changhr.concurrency.java8.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * @author changhr
 * @create 2020-04-01 16:10
 */
public class ForkJoinTest {

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

        ForkJoinTask<Integer> result = forkJoinPool.submit(new Task(1, 10000));

        System.out.println("计算结果为：" + result.join());
        forkJoinPool.shutdown();
    }

}
