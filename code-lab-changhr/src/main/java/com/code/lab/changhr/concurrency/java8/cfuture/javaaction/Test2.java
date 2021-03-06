package com.code.lab.changhr.concurrency.java8.cfuture.javaaction;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * @author changhr
 * @create 2020-03-30 17:59
 */
public class Test2 {

    private static List<Shop> shops = Arrays.asList(
            new Shop("BestPrice"),
            new Shop("LetsSaveBig"),
            new Shop("MyFavoriteShop"),
            new Shop("BuyItAll"),
            new Shop("OtherShop")
    );


    private static final Executor executor =
            Executors.newFixedThreadPool(Math.min(shops.size(), 100),
                    r -> {
                        Thread thread = new Thread(r);
                        // Java 程序无法终止或者退出一个正在运行中的线程，
                        // 所以最后剩下的那个线程会由于一直等待无法发生的事件而引发问题。
                        // 与此相反，如果将线程标记为守护进程，意味着程序退出时它也会被回收。
                        thread.setDaemon(true);
                        return thread;
                    });

    public static void main(String[] args) {
        System.out.println("processors count: " + Runtime.getRuntime().availableProcessors());

        long start = System.nanoTime();

        // 采用顺序查询
        //System.out.println(findPrices("myPhone27s"));

        // 采用并行查询
        //System.out.println(findPricesParallel("myPhone27s"));

        // 采用 CompletableFuture
        System.out.println(findPricesCompletableFuture("myPhone27s"));

        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs.");
    }

    /**
     * 采用顺序查询所有商店的方式实现的 findPrices 方法
     */
    public static List<String> findPrices(String product) {
        return shops.stream()
                .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
                .collect(Collectors.toList());
    }

    /**
     * 对 findPrices 进行并行操作
     */
    public static List<String> findPricesParallel(String product) {
        return shops.parallelStream()
                .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
                .collect(Collectors.toList());
    }

    public static List<String> findPricesCompletableFuture(String product) {
        List<CompletableFuture<String>> priceFutures = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(
                        () -> shop.getName() + "price is " + shop.getPrice(product), executor))
                .collect(Collectors.toList());

        // 等待所有异步操作结束
        return priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

}
