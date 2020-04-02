package com.code.lab.changhr.concurrency.java8.future;

import org.springframework.util.concurrent.ListenableFutureTask;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author changhr
 * @create 2020-04-02 17:00
 */
public class FutureTaskTest {

    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static Integer getNextSequence() {
        return atomicInteger.getAndIncrement();
    }

    private static final Executor EXECUTORS = new ThreadPoolExecutor(
            8, 16, 0, TimeUnit.MICROSECONDS,
            new LinkedBlockingQueue<>(256),
            r -> {
                Thread thread = new Thread(r);
                thread.setDaemon(false);
                thread.setName("work-thread-" + getNextSequence());
                return thread;
            }
    );

    public static void main(String[] args) {
        ListenableFutureTask<String> futureTask = new ListenableFutureTask<>(() -> {
            TimeUnit.SECONDS.sleep(1);
            return Thread.currentThread().getName() + " hello";
        });

        futureTask.addCallback(
                result -> System.out.println(result + " world"),
                ex -> System.out.println(ex.getMessage())
        );

        EXECUTORS.execute(futureTask);
    }

}
