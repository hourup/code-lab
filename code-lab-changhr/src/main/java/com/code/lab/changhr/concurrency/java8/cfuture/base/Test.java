package com.code.lab.changhr.concurrency.java8.cfuture.base;

import cn.hutool.core.lang.func.VoidFunc0;
import com.code.lab.changhr.concurrency.utils.Delay;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author changhr2013
 * @date 2020/4/4
 */
public class Test {
    /** 工作线程池 */
    private static final AtomicLong THREAD_COUNT = new AtomicLong(0L);
    private static final ExecutorService EXECUTORS = new ThreadPoolExecutor(
            8, 16, 0, TimeUnit.MICROSECONDS,
            new LinkedBlockingQueue<>(256),
            runnable -> {
                Thread thread = new Thread(runnable);
                thread.setDaemon(false);
                thread.setName("work-thread-" + THREAD_COUNT.getAndIncrement());
                return thread;
            }
    );

    public static void main(String[] args) throws InterruptedException {

        Callable<Long> callable1 = () -> {
            Delay.delay();
            return System.currentTimeMillis();
        };
        Callable<Long> callable2 = () -> {
            Delay.delay();
            return System.currentTimeMillis();
        };
        Callable<Long> callable3 = () -> {
            Delay.delay();
            return System.currentTimeMillis();
        };
        Callable<Long> callable4 = () -> {
            Delay.delay();
            return System.currentTimeMillis();
        };
        Callable<Long> callable5 = () -> {
            Delay.delay();
            return System.currentTimeMillis();
        };

        List<Callable<Long>> callableList = Arrays.asList(callable1, callable2, callable3, callable4, callable5);


        List<Future<Long>> futureList = EXECUTORS.invokeAll(callableList);

        futureList.forEach(longFuture -> {
            Long result = null;
            try {
                result = longFuture.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(result);
        });
    }
}
