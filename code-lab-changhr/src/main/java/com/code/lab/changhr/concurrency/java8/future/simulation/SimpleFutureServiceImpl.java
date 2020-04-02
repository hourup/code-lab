package com.code.lab.changhr.concurrency.java8.future.simulation;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * SimpleFutureServiceImpl 的主要作用在于当提交任务时创建一个新的线程来受理该任务，
 * 从而达到任务异步执行的效果。
 *
 * @author changhr
 * @create 2020-04-02 14:57
 */
public class SimpleFutureServiceImpl<IN, OUT> implements SimpleFutureService<IN, OUT> {

    private static final String FUTURE_THREAD_PREFIX = "SIMPLE-FUTURE-";

    private final AtomicInteger nextCounter = new AtomicInteger(0);

    private String getNextName() {
        return FUTURE_THREAD_PREFIX + nextCounter.getAndIncrement();
    }

    @Override
    public SimpleFuture<?> submit(Runnable runnable) {
        final SimpleFutureTask<Void> future = new SimpleFutureTask<>();

        new Thread(() -> {
            runnable.run();
            // 任务执行结束之后将 null 作为结果传给 future
            future.finish(null);
        }, getNextName()).start();

        return future;
    }

    @Override
    public SimpleFuture<OUT> submit(Task<IN, OUT> task, IN input) {
        final SimpleFutureTask<OUT> future = new SimpleFutureTask<>();

        new Thread(() -> {
            OUT result = task.get(input);
            // 任务执行结束之后，将真实的结果通过 finish 方法传递给 future
            future.finish(result);
        }).start();

        return future;
    }
}
