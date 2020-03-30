package com.code.lab.changhr.concurrency.java8.cfuture;

import com.code.lab.changhr.concurrency.utils.Delay;

import java.util.concurrent.Future;

/**
 * Test No.1：测试使用异步 API
 *
 * @author changhr
 * @create 2020-03-30 17:28
 */
public class Test1 {

    public static void main(String[] args) {
        Shop shop = new Shop("BestShop");
        long start = System.nanoTime();

        Future<Double> futurePrice = shop.getPriceAsync("my favorite product");

        long invocationTime = (System.nanoTime() - start) / 1_000_000;
        System.out.println("invocation returned after " + invocationTime + " msecs.");

        // 执行更多任务，比如查询其他商店
        doSomethingElse();

        // 在计算商品价格的同时
        try {
            // 从 Future 对象中读取价格，如果价格未知，会发生阻塞
            Double price = futurePrice.get();
            System.out.printf("Price is %.2f%n", price);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        long retrievalTime = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Price returned after " + retrievalTime + " msecs.");
    }

    private static void doSomethingElse() {
        Delay.delay();
    }

}
