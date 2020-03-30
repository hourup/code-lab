package com.code.lab.changhr.concurrency.utils;

import cn.hutool.core.util.RandomUtil;

/**
 * 用来模拟延迟
 *
 * @author changhr
 * @create 2020-03-30 10:20
 */
public class Delay {

    private Delay() {
    }

    /**
     * 延迟指定时间
     *
     * @param delayTime 延迟时间，ms
     */
    public static Integer delay(Integer delayTime) {
        try {
            Thread.sleep(delayTime);
            return delayTime;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 延迟随机时间，1000 ms 以内
     *
     * @return Integer 延迟的时间
     */
    public static Integer delay() {
        try {
            int delayTime = RandomUtil.randomInt(1000);
            Thread.sleep(delayTime);
            return delayTime;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
