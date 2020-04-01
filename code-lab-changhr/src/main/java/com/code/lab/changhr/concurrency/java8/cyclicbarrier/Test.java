package com.code.lab.changhr.concurrency.java8.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.stream.IntStream;

/**
 * @author changhr
 * @create 2020-04-01 16:45
 */
public class Test {

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(4, () -> System.out.println("人满了发车!"));

        IntStream.rangeClosed(1, 12).forEach(
                number -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    new Thread(() -> {
                        try {
                            System.out.println("第 " + number + " 位乘客上车了！");
                            cyclicBarrier.await();
                            System.out.println("第 " + number + " 位乘客出发了！");
                        } catch (InterruptedException | BrokenBarrierException e) {
                            e.printStackTrace();
                        }
                    }).start();
                }
        );
    }

}
