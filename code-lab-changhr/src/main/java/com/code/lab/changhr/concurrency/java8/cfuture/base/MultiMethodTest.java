package com.code.lab.changhr.concurrency.java8.cfuture.base;

import com.code.lab.changhr.concurrency.utils.Delay;
import org.apache.commons.lang3.time.StopWatch;

import java.time.LocalTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * 多个 CompletableFuture 的处理
 *
 * @author changhr
 * @create 2020-04-01 10:50
 */
public class MultiMethodTest {

    public static void main(String[] args) {
        thenCombine();
    }

    /**
     * 把两个 CompletableFuture 的任务都执行完成后，把两个任务的结果一块交给 thenCombine 来处理。
     * thenCombine 是可以有返回的。
     */
    public static void thenCombine() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        CompletableFuture<String> cFuture = CompletableFuture.supplyAsync(() -> {
            Delay.delay(1000);
            printWithTime("first CompletableFuture");
            return "first";
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            Delay.delay(1000);
            printWithTime("second CompletableFuture");
            return "second";
        }), (result1, result2) -> {
            printWithTime(result1 + " " + result2);
            return result1 + " " + result2;
        });

        System.out.println("pre join 耗时：" + stopWatch.getTime(TimeUnit.MILLISECONDS));
        cFuture.join();
        System.out.println("post join 耗时：" + stopWatch.getTime(TimeUnit.MILLISECONDS));
        stopWatch.stop();
    }

    private static void printWithTime(String record) {
        LocalTime now = LocalTime.now();
        System.out.println(now + ": " + record);
    }
}
