package com.code.lab.changhr.concurrency.java8.future;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author changhr
 * @create 2020-04-03 17:07
 */
public class SimpleFutureTask<V> extends FutureTask<V> {

    private String taskName;

    private long startTime;

    @Override
    public void run() {
        this.startTime = System.currentTimeMillis();
        super.run();
    }

    public SimpleFutureTask(Callable<V> callable, String taskName) {
        super(callable);
        this.taskName = taskName;
    }

    public SimpleFutureTask(Runnable runnable, V result, String taskName) {
        super(runnable, result);
        this.taskName = taskName;
    }

    @Override
    protected void done() {
        System.out.println("Simple Task [" + taskName + "] is Done. consume " + (System.currentTimeMillis() - startTime) + "ms");
    }
}
