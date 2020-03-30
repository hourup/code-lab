package com.code.lab.changhr.concurrency.guava;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.*;
import org.apache.commons.lang3.time.StopWatch;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author changhr
 * @create 2020-03-26 18:10
 */
public class ListenableFutureDemo3 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        List<String> resultList = Lists.newArrayList();

        ListeningExecutorService service = MoreExecutors.listeningDecorator(
                new ThreadPoolExecutor(8, 16,
                        0L, TimeUnit.SECONDS,
                        new LinkedBlockingDeque<>(256),
                        new ThreadFactoryBuilder().setNameFormat("work-thread-%d").build()));

        ListenableFuture<String> work1Future = service.submit(new BuildStringCallable("first"));
        ListenableFuture<String> work2Future = service.submit(new BuildStringCallable("second"));
        ListenableFuture<String> work3Future = service.submit(new BuildStringCallable("three"));

        ListenableFuture<List<String>> composeFutures = Futures.allAsList(work1Future, work2Future, work3Future);

        //Futures.addCallback(composeFutures, new FutureCallback<List<String>>() {
        //
        //    @Override
        //    public void onSuccess(@Nullable List<String> result) {
        //            assert result != null;
        //            resultList.addAll(result);
        //    }
        //
        //    @Override
        //    public void onFailure(Throwable t) {
        //            throw new RuntimeException(t);
        //    }
        //}, service);

        resultList = composeFutures.get();

        resultList.forEach(System.out::println);

        stopWatch.stop();
        System.out.println("耗时：" + stopWatch.getTime(TimeUnit.MILLISECONDS));

        service.shutdown();
    }


}
