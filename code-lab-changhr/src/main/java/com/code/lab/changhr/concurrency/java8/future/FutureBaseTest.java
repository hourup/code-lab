package com.code.lab.changhr.concurrency.java8.future;

import com.code.lab.changhr.concurrency.utils.Delay;
import org.apache.commons.lang3.time.StopWatch;

import java.util.concurrent.*;

/**
 * @author changhr
 * @create 2020-03-30 16:33
 */
public class FutureBaseTest {

    public static void main(String[] args) {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        ExecutorService executor = Executors.newCachedThreadPool();

        // 向 ExecutorService 提交一个 Callable 对象
        Future<Double> future = executor.submit(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                // 以异步方式在新的线程中执行耗时的操作
                return doSomeLongComputation();
            }
        });

        System.out.println("提交完线程：" + stopWatch.getTime(TimeUnit.MILLISECONDS) + "ms");
        // 异步操作进行的同时，你可以做其他的事情
        doSomethingElse();

        try {
            // 获取异步操作的结果，如果最终被阻塞，无法得到结果，那么在最多等待 1 秒钟之后退出
            Double result = future.get(1, TimeUnit.SECONDS);

            System.out.println("计算结果：" + result);
        } catch (ExecutionException e) {
            // 计算抛出一个异常
        } catch (InterruptedException e) {
            // 当前线程再等待过程中被中断
        } catch (TimeoutException e) {
            // 在 Future 对象完成之前超过已过期
        }

        System.out.println("耗时：" + stopWatch.getTime(TimeUnit.MILLISECONDS) + "ms");
        stopWatch.stop();
        executor.shutdown();
    }

    public static Double doSomeLongComputation() {
        System.out.println("do some long computation.");
        Integer delayTime = Delay.delay(1000);
        return new Double(delayTime);
    }

    public static Double doSomethingElse() {
        System.out.println("do something else.");
        Integer delayTime = Delay.delay(1000);
        return new Double(delayTime);
    }

}
