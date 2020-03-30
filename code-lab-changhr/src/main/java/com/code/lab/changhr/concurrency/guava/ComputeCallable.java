package com.code.lab.changhr.concurrency.guava;

import cn.hutool.core.util.RandomUtil;
import com.code.lab.changhr.concurrency.guava.utils.Delay;

import java.util.concurrent.Callable;

/**
 * 模拟一个耗时任务，随机耗时 0 - 1000 ms
 *
 * @author changhr
 * @create 2019-08-02 11:22
 */
public class ComputeCallable implements Callable<Integer> {

    @Override
    public Integer call() {
        System.out.println("当前线程为：" + Thread.currentThread().getName());
        int delayTime = RandomUtil.randomInt(1000);
        Delay.delay(delayTime);
        return delayTime;
    }
}
