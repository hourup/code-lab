package com.code.lab.changhr.concurrency.java8.future.simulation;

import java.util.concurrent.TimeUnit;

/**
 * @author changhr
 * @create 2020-04-02 15:17
 */
public class RunnableTest {

    public static void main(String[] args) throws InterruptedException {

        // 定义不需要返回值的 SimpleFutureService
        SimpleFutureService<Void, Void> service = SimpleFutureService.newService();

        // submit 方法为立即返回的方法
        SimpleFuture<?> simpleFuture = service.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Simple Task finish done.");
        });

        System.out.println("I am Main Thread.");
        // get 方法会使当前线程进入阻塞
        simpleFuture.get();
        System.out.println("Now Task should be done.");
    }
}
