package com.code.lab.changhr.concurrency.java8.cfuture.base;

import com.code.lab.changhr.concurrency.utils.Delay;
import org.apache.commons.lang3.time.StopWatch;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.*;

/**
 * 单个 CompletableFuture 处理方法
 *
 * @author changhr
 * @create 2020-04-01 9:20
 */
public class SingleMethodTest {

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(8);

        // 实例化：一共有四种
        // runAsync 不带返回值，接收一个 Runnable
        // Runnable 是函数式接口
        CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                System.out.println("run...");
            }
        });

        // 第二个参数可选，默认情况下使用 ForkJoinPool.commonPool()
        CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                System.out.println("run with executor...");
            }
        }, executor);

        // supplyAsync 有返回值，接收一个 Supplier
        // Supplier 是函数式接口
        CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                return "supplier...";
            }
        });

        CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                return "supplier with executor...";
            }
        }, executor);

        //thenApply();

        //thenAccept();

        //thenRun();

        //handle();

        whenCompleteWithSupply();
    }

    /**
     * 该方法使用的参数为 Runnable，没有输入输出参数
     */
    public static void thenRun() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        CompletableFuture<Void> cFuture = CompletableFuture.supplyAsync(() -> Delay.delay(1000))
                .thenRun(new Runnable() {
                    @Override
                    public void run() {
                        Delay.delay(1000);
                        System.out.println("thenRun: hello world1!");
                    }
                })
                .thenRun(() -> {
                    Delay.delay(1000);
                    System.out.println("thenRun: hello world2!");
                });

        cFuture.join();

        stopWatch.stop();
        System.out.println("耗时：" + stopWatch.getTime(TimeUnit.MILLISECONDS));
    }

    /**
     * 该方法使用的参数为 Function<A, B>，第一个泛型为输入参数的类型，第二个参数为输出参数的类型。
     * 方法返回类型为 CompletableFuture
     */
    public static void thenAccept() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        CompletableFuture<Void> cFuture = CompletableFuture.supplyAsync(() -> Delay.delay(1000))
                .thenAccept(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer delay) {
                        int secondDelay = Delay.delay(1000) + delay;
                        System.out.println("second delay: " + secondDelay);
                    }
                })
                .thenAccept(aVoid -> System.out.println("hello"));
        cFuture.join();

        stopWatch.stop();
        System.out.println("耗时：" + stopWatch.getTime(TimeUnit.MILLISECONDS));
    }

    /**
     * 该方法使用的参数为 Function<A, B>，第一个泛型为输入参数的类型，第二个参数为输出参数的类型
     * 方法返回类型为 CompletableFuture
     * thenApply 是持续在一条线程上执行的
     */
    public static void thenApply() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        CompletableFuture<Integer> cFuture = CompletableFuture.supplyAsync(() -> Delay.delay(1000))
                .thenApply(delay -> Delay.delay(1000) + delay)
                .thenApply(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer doubleDelay) {
                        return Delay.delay(1000) + doubleDelay;
                    }
                });

        Integer result = cFuture.join();
        System.out.println(result);

        stopWatch.stop();
        System.out.println("耗时：" + stopWatch.getTime(TimeUnit.MILLISECONDS));
    }

    /**
     * 用于方法执行后执行，可以接受上级处理结果和上级方法异常
     */
    public static void handle() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        CompletableFuture<Integer> ccFuture = CompletableFuture.supplyAsync(() -> Delay.delay(1000))
                .handle(new BiFunction<Integer, Throwable, Integer>() {
                    @Override
                    public Integer apply(Integer delay, Throwable throwable) {
                        if (throwable != null) {
                            System.out.println(throwable.getMessage());
                            throw new RuntimeException(throwable);
                        }
                        return Delay.delay(1000) + delay;
                    }
                });

        Integer result = ccFuture.join();
        System.out.println(result);

        stopWatch.stop();
        System.out.println("耗时：" + stopWatch.getTime(TimeUnit.MILLISECONDS));
    }

    /**
     * whenComplete 对执行结果进行处理，不影响 get 方法获取最初的返回值。
     * 当发生异常后 exceptionally 方法会执行，对异常进行处理。
     */
    public static void whenCompleteWithSupply() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        CompletableFuture<Optional<Integer>> cFuture = CompletableFuture
                .supplyAsync(() -> {
                    if (false) {
                        throw new RuntimeException("hello exception!");
                    }
                    return Optional.ofNullable(Delay.delay(1000));
                })
                .exceptionally(new Function<Throwable, Optional<Integer>>() {
                    @Override
                    public Optional<Integer> apply(Throwable throwable) {
                        return Optional.empty();
                    }
                })
                .whenComplete(new BiConsumer<Optional<Integer>, Throwable>() {
                    @Override
                    public void accept(Optional<Integer> result, Throwable throwable) {
                        result.ifPresent(System.out::println);
                    }
                });

        cFuture.join();

        stopWatch.stop();
        System.out.println("耗时：" + stopWatch.getTime(TimeUnit.MILLISECONDS));
    }

}
