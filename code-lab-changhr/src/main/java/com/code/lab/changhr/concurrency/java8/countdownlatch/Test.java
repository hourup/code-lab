package com.code.lab.changhr.concurrency.java8.countdownlatch;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.*;

/**
 * @author changhr
 * @create 2020-04-01 18:06
 */
public class Test {

    private static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(
            8, 16, 0, TimeUnit.MICROSECONDS,
            new LinkedBlockingQueue<>(256),
            r -> {
                Thread thread = new Thread(r);
                thread.setDaemon(true);
                return thread;
            }
    );

    private static final CountDownLatch countDownLatch = new CountDownLatch(5);

    @Data
    @AllArgsConstructor
    public static class PrepareTask implements Runnable {
        private String name;
        @Override
        public void run() {
            System.out.println(name + "到位！");
            countDownLatch.countDown();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        System.out.println("控球后卫到位！等待所有位置球员到位！");
        countDownLatch.countDown();

        EXECUTOR.submit(new PrepareTask("得分后卫"));
        EXECUTOR.submit(new PrepareTask("中锋"));
        EXECUTOR.submit(new PrepareTask("大前锋"));
        EXECUTOR.submit(new PrepareTask("小前锋到位"));

        countDownLatch.await();
        System.out.print("全部到位，开始进攻！");
    }

}
