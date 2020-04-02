package com.code.lab.changhr.concurrency.java8.future.simulation;

/**
 * @author changhr
 * @create 2020-04-02 14:52
 */
public interface SimpleFutureService<IN, OUT> {

    /**
     * 提交不需要返回值的任务，SimpleFuture.get 方法返回的将会是 null
     */
    SimpleFuture<?> submit(Runnable runnable);

    /**
     * 提交需要返回值的任务，其中 Task 接口代替了 Runnable 接口
     */
    SimpleFuture<OUT> submit(Task<IN, OUT> task, IN input);

    /**
     * 使用静态方法创建一个 SimpleFutureService 的实现
     */
    static <T, R> SimpleFutureService<T, R> newService() {
        return new SimpleFutureServiceImpl<>();
    }
}
