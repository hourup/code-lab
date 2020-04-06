package com.code.lab.changhr.concurrency.java8.cfuture.querydemo;

import org.apache.commons.lang3.time.StopWatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author changhr2013
 * @date 2020/4/5
 */
public class Test {

    /** 工作线程池 */
    private static final AtomicLong THREAD_COUNT = new AtomicLong(0L);
    public static final ExecutorService EXECUTORS = new ThreadPoolExecutor(
            32, 64, 0, TimeUnit.MICROSECONDS,
            new LinkedBlockingQueue<>(256),
            runnable -> {
                Thread thread = new Thread(runnable);
                thread.setDaemon(false);
                thread.setName("query-thread-" + THREAD_COUNT.getAndIncrement());
                return thread;
            }
    );

    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        int systemThread = Runtime.getRuntime().availableProcessors();
        System.out.println("system processor number: " + systemThread);

        List<UserInfo> userInfoList = Collections.synchronizedList(new ArrayList<>());

        IntStream.rangeClosed(1, 20).forEach(
                index -> {
                    UserInfo userInfo = new UserInfo()
                            .setId(index)
                            .setName("username" + index)
                            .setCarId(index)
                            .setJobId(index)
                            .setHomeId(index);
                    userInfoList.add(userInfo);
                }
        );

        QueryUserService queryUserService = new QueryUserService();

        // 基本的循环查询
//        baseMethod(userInfoList, queryUserService);

//        userInfoList.forEach(
//                userInfo -> queryUserService.convertUserInfoAsync(userInfo).join()
//        );

        List<CompletableFuture<UserInfo>> collect = userInfoList.stream()
                .map(queryUserService::convertUserInfoAsync)
                .collect(Collectors.toList());

        CompletableFuture<?>[] futureArray = collect.toArray(new CompletableFuture<?>[collect.size()]);

        CompletableFuture.allOf(futureArray).join();

        // 方式一：使用 CompletableFuture 和自定义的线程池
//        completableFutureMethod(userInfoList, queryUserService);

        // 方式二：使用并行流默认线程池，
//        parallelMethod(userInfoList, queryUserService);

        userInfoList.forEach(System.out::println);

        stopWatch.stop();
        System.out.printf("consume time: %dms%n", stopWatch.getTime(TimeUnit.MILLISECONDS));
    }

    private static void baseMethod(List<UserInfo> userInfoList, QueryUserService queryUserService) {

        userInfoList.stream()
                .map(queryUserService::convertUserInfo)
                .collect(Collectors.toList());
    }

    private static void completableFutureMethod(List<UserInfo> userInfoList, QueryUserService queryUserService) {
        ArrayList<CompletableFuture<UserInfo>> resultList = new ArrayList<>();
        for (UserInfo userInfo : userInfoList) {
            CompletableFuture<UserInfo> resultFuture = CompletableFuture.supplyAsync(() -> queryUserService.convertUserInfo(userInfo),EXECUTORS);
            resultList.add(resultFuture);
        }

        CompletableFuture<?>[] completableFutureArray = new CompletableFuture<?>[resultList.size()];
        for (int i = 0; i < completableFutureArray.length; i++) {
            completableFutureArray[i] = resultList.get(i);
        }

        CompletableFuture.allOf(completableFutureArray).join();
    }

    private static void parallelMethod(List<UserInfo> userInfoList, QueryUserService queryUserService) {
        // 方式二：使用并行流默认线程池，
        Stream.iterate(0, i -> i + 1).limit(20).map(
                number ->
                        userInfoList.stream()
                                .skip(number * Runtime.getRuntime().availableProcessors())
                                .limit(Runtime.getRuntime().availableProcessors())
                                .parallel()
                                .map(queryUserService::convertUserInfo)
                                .collect(Collectors.toList())
        ).collect(Collectors.toList());
    }
}
