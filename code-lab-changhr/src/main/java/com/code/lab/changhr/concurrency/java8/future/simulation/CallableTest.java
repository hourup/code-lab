package com.code.lab.changhr.concurrency.java8.future.simulation;

import java.util.concurrent.TimeUnit;

/**
 * @author changhr
 * @create 2020-04-02 15:23
 */
public class CallableTest {

    public static void main(String[] args) throws InterruptedException {

        // 定义有返回值的 SimpleFutureService
        SimpleFutureService<String, Integer> service = SimpleFutureService.newService();

        // submit 方法会立即返回
        SimpleFuture<Integer> simpleFuture = service.submit(input -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return input.length();
        }, "hello");

        SimpleFuture<Integer> simpleFuture2 = service.submit(input -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return input.length();
        }, "world!");

        System.out.println("I am Main Thread.");
        // get 方法使当前线程进入阻塞，最终会返回计算的结果
        System.out.println(simpleFuture.get());

        System.out.println(simpleFuture2.get());
        System.out.println("Now Task should be done.");
    }

}
