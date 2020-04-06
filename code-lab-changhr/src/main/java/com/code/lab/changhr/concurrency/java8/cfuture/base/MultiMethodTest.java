package com.code.lab.changhr.concurrency.java8.cfuture.base;

import com.code.lab.changhr.concurrency.utils.Delay;
import org.apache.commons.lang3.time.StopWatch;

import java.time.LocalTime;
import java.util.concurrent.*;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;


/**
 * 多个 CompletableFuture 的处理
 *
 * @author changhr
 * @create 2020-04-01 10:50
 */
public class MultiMethodTest {


    public static void main(String[] args) {



//        thenCombine();

//        thenAcceptBoth();

//        applyToEither();

//        acceptEither();

//        runAfterEither();

//        runAfterBoth();

        thenCompose();
    }

    /**
     * 把两个 CompletableFuture 的任务都执行完成后，把两个任务的结果一块交给 thenCombine 来处理。
     * thenCombine 是可以有返回的。
     */
    public static void thenCombine() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        CompletableFuture<String> cFuture = CompletableFuture.supplyAsync(() -> {
            Delay.delay();
            printWithTime("first CompletableFuture");
            return "first";
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            Delay.delay();
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

    /**
     * 接收两个线程的返回结果，并做出处理。
     * 这个方法是消费式的，没有返回。
     * <p>
     * 注意这里的返回是指自定义接口方法中的返回。
     * 基本上所有的方法都会返回一个 CompletionStage，至于泛型则由接口方法返回。
     */
    public static void thenAcceptBoth() {
        CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> {
            Integer delay = Delay.delay();
            printWithTime("f1 = " + delay);
            return delay;
        });

        CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> {
            Integer delay = Delay.delay();
            printWithTime("f2 = " + delay);
            return delay;
        });

        f1.thenAcceptBoth(f2, (f11, f21) -> printWithTime("f1 = " + f11 + ", f2 = " + f21)).join();
    }

    /**
     * 哪个线程先返回就使用谁的返回结果进入该方法。
     * 另一个线程如果执行时间过长则不再执行。
     * 该方法参数为函数式接口，有返回类型。
     */
    public static void applyToEither() {
        CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> {
            Integer delay = Delay.delay();
            printWithTime("f1 = " + delay);
            return delay;
        });

        CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> {
            Integer delay = Delay.delay();
            printWithTime("f2 = " + delay);
            return delay;
        });

        CompletableFuture<Integer> resultFuture = f1.applyToEither(f2, new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer integer) {
                printWithTime("applyToEither: " + integer);
                return integer * 2;
            }
        });

        Integer result = resultFuture.join();
        printWithTime("result = " + result);

        Delay.delay(1000);
    }

    /**
     * 行为与 applyToEither 类似，但是该方法是消费式，线程不会返回执行结果。
     */
    public static void acceptEither() {
        CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> {
            Integer delay = Delay.delay();
            printWithTime("f1 = " + delay);
            return delay;
        });

        CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> {
            Integer delay = Delay.delay();
            printWithTime("f2 = " + delay);
            return delay;
        });

        f1.acceptEither(f2, integer -> printWithTime("result = " + integer)).join();

        Delay.delay(1000);
    }

    /**
     * 非阻塞
     * 只有在该方法执行之前有现成返回该方法才会执行。
     */
    public static void runAfterEither() {
        CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> {
            Integer delay = Delay.delay();
            printWithTime("f1 = " + delay);
            return delay;
        });

        CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> {
            Integer delay = Delay.delay();
            printWithTime("f2 = " + delay);
            return delay;
        });

        f1.runAfterEither(f2, () -> printWithTime("this is a result.")).join();

        Delay.delay(1000);
    }

    /**
     * 两个线程都执行完成后，执行该方法。
     */
    public static void runAfterBoth() {
        CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> {
            Integer delay = Delay.delay();
            printWithTime("f1 = " + delay);
            return delay;
        });

        CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> {
            Integer delay = Delay.delay();
            printWithTime("f2 = " + delay);
            return delay;
        });

        f1.runAfterBoth(f2, () -> printWithTime("this is a result")).join();
    }

    /**
     * 将两个线程串行连接起来，只有第一个线程返回结果时，才会将返回值作为参数传给第二个线程执行。
     */
    public static void thenCompose() {
        CompletableFuture.supplyAsync(() -> {
            Integer delay = Delay.delay();
            printWithTime("t1 = " + delay);
            return delay;
        }).thenCompose(t1Result -> CompletableFuture.supplyAsync(() -> {
            printWithTime("t2 start");
            printWithTime("we can use t1 result: " + t1Result);
            Integer delay = Delay.delay();
            printWithTime("t2 = " + delay);
            return delay;
        })).join();
    }

    private static void printWithTime(String record) {
        LocalTime now = LocalTime.now();
        System.out.println(now + ": " + record);
    }
}
