package com.code.lab.changhr.concurrency.java8.exchanger;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

/**
 * @author changhr
 * @create 2020-04-03 15:49
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {
        Exchanger<String> workmate = new Exchanger<>();

        Thread michael = new Thread(() -> {
            String threadName = Thread.currentThread().getName();
            try {
                System.out.println(threadName + ": I'm Michael and want to delivery a phone to my friend.");
                System.out.println(threadName + ": There is an emergency meeting.");
                System.out.println(threadName + ": I give the phone to my workmate.");
                System.out.println(threadName + ": He will help me to delivery the phone and return the express document to me.");

                String expressDocument = workmate.exchange("---phone---");
                System.out.println(threadName + ": I got the express document " + expressDocument);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }, "Michael");

        Thread deliveryman = new Thread(() -> {
            String threadName = Thread.currentThread().getName();

            try {
                System.out.println(threadName + ": I'm a deliveryman.");
                System.out.println(threadName + ": I'm going to get Michael's express from his workmate and give the express document to his workmate.");

                String expressGoods = workmate.exchange("---phone express document---");
                System.out.println(threadName + ": I got the goods of " + expressGoods);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Deliveryman");

        michael.start();
        TimeUnit.MILLISECONDS.sleep(100);
        deliveryman.start();
    }

}
