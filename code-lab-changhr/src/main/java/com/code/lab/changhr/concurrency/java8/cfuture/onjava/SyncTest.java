package com.code.lab.changhr.concurrency.java8.cfuture.onjava;

import org.apache.commons.lang3.time.StopWatch;

import java.util.concurrent.TimeUnit;

/**
 * @author changhr2013
 * @date 2020/3/31
 */
public class SyncTest {

    public static void main(String[] args) {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // 准备鸡蛋、牛奶、糖、面粉
        MaterialStore.Eggs eggs = new MaterialStore.Eggs();
        MaterialStore.Milk milk = new MaterialStore.Milk();
        MaterialStore.Sugar sugar = new MaterialStore.Sugar();
        MaterialStore.Flour flour = new MaterialStore.Flour();

        // 混合搅拌原材料制作面糊
        MaterialStore.Batter batter = new MaterialStore.Batter(eggs, milk, sugar, flour);

        // 烘烤面糊
        MaterialStore.Baked baked = new MaterialStore.Baked(batter);

        // 制作蛋糕上的奶油
        MaterialStore.Frosting frosting = new MaterialStore.Frosting();

        // 制作蛋糕
        MaterialStore.FrostCake frostCake = new MaterialStore.FrostCake(frosting, baked);

        stopWatch.stop();
        System.out.println("总耗时：" + stopWatch.getTime(TimeUnit.MILLISECONDS) + "ms");
    }
}
