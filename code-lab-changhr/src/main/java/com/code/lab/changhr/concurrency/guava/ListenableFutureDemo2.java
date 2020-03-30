package com.code.lab.changhr.concurrency.guava;

import com.code.lab.changhr.concurrency.utils.Delay;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.time.StopWatch;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * @author changhr
 * @create 2019-08-02 11:06
 */
@SuppressWarnings("Duplicates")
public class ListenableFutureDemo2 {

    public static void main(String[] args) throws InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        ConcurrentMap<String, Integer> resultMap = Maps.newConcurrentMap();

        Integer delayTime1 = Delay.delay();
        resultMap.put(Thread.currentThread().getName() + 1, delayTime1);

        Integer delayTime2 = Delay.delay();
        resultMap.put(Thread.currentThread().getName() + 2, delayTime2);

        resultMap.forEach((key, value) -> System.out.println(key + " : " + value));

        stopWatch.stop();
        System.out.println(stopWatch.getTime(TimeUnit.MILLISECONDS));
    }
}
