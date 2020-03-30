package com.code.lab.changhr.concurrency.guava;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.*;
import org.apache.commons.lang3.time.StopWatch;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.concurrent.*;

/**
 * @author changhr
 * @create 2019-08-02 11:06
 */
@SuppressWarnings("Duplicates")
public class ListenableFutureDemo1 {

    public static void main(String[] args) throws InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // 存放最终结果的 map
        ConcurrentMap<String, Integer> resultMap = Maps.newConcurrentMap();

        // 初始化线程池
        ListeningExecutorService service = MoreExecutors.listeningDecorator(
                new ThreadPoolExecutor(8, 16, 0L, TimeUnit.SECONDS,
                        new LinkedBlockingDeque<>(256),
                        new ThreadFactoryBuilder().setNameFormat("work-thread-%d").build()));

        // 计数器
        CountDownLatch countDownLatch = new CountDownLatch(3);

        // 提交给线程池任务
        ListenableFuture<Integer> work1Future = service.submit(new ComputeCallable());
        ListenableFuture<Integer> work2Future = service.submit(new ComputeCallable());

        // 为任务一添加异步执行函数，返回 queueWork1Future
        // 返回一个新的 ListenableFuture，其结果是将给定的 AsyncFunction 应用于给定的 ListenableFuture 的结果
        ListenableFuture<Integer> queueWork1Future = Futures.transformAsync(work1Future,
                new AsyncFunction<Integer, Integer>() {
                    @Override
                    public ListenableFuture<Integer> apply(@Nullable Integer input) throws Exception {
                        return service.submit(() -> input + 1);
                    }
                }, service);

        // 为 queueWork1Future 添加回调
        Futures.addCallback(queueWork1Future, new FutureCallback<Integer>() {
            @Override
            public void onSuccess(@Nullable Integer result) {
                try {
                    assert result != null;
                    resultMap.put(Thread.currentThread().getName(), result);
                } finally {
                    countDownLatch.countDown();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                try {
                    t.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            }
        }, service);

        // 为任务一添加回调
        Futures.addCallback(work1Future, new FutureCallback<Integer>() {
            @Override
            public void onSuccess(@Nullable Integer result) {
                try {
                    resultMap.put(Thread.currentThread().getName(), result);
                } finally {
                    countDownLatch.countDown();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                try {
                    t.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            }
        }, service);

        // 为任务二添加回调
        Futures.addCallback(work2Future, new FutureCallback<Integer>() {
            @Override
            public void onSuccess(@Nullable Integer result) {
                try {
                    resultMap.put(Thread.currentThread().getName(), result);
                } finally {
                    countDownLatch.countDown();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                try {
                    t.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            }
        }, service);

        // 等待计数器归 0
        countDownLatch.await();
        // 打印结果
        resultMap.forEach((key, value) -> System.out.println(key + " : " + value));

        // 记录运行时间
        stopWatch.stop();
        System.out.println("耗时：" + stopWatch.getTime(TimeUnit.MILLISECONDS));

        // 关闭线程池
        service.shutdown();
    }
}
