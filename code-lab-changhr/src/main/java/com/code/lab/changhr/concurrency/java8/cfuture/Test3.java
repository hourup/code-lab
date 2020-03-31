package com.code.lab.changhr.concurrency.java8.cfuture;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * @author changhr
 * @create 2020-03-31 15:44
 */
public class Test3 {

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

        long start = System.nanoTime();

        //findPrices("iphone11s Pro Max");

        // 使用 CompletableFuture
        findPricesCompletableFuture("iphone11s Pro Max");

        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs.");
    }

    public static List<String> findPrices(String product) {
        return shops.stream()
                .map(shop -> shop.getPriceWithDiscountCode(product))
                // Quote 负责对 shop 返回的字符串进行转换
                .map(Quote::parse)
                // 联系 Discount 服务，为每个 Quote 申请折扣
                .map(Discount::applyDiscount)
                .collect(Collectors.toList());
    }

    public static List<String> findPricesCompletableFuture(String product) {
        List<CompletableFuture<String>> priceFutures = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPriceWithDiscountCode(product), executor))
                .map(future -> future.thenApply(Quote::parse))
                .map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executor)))
                .collect(Collectors.toList());

        return priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }
}
