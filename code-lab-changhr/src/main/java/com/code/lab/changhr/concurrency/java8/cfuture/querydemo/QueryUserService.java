package com.code.lab.changhr.concurrency.java8.cfuture.querydemo;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

/**
 * @author changhr2013
 * @date 2020/4/5
 */
public class QueryUserService {

    /** 工作线程池 */
    private static final AtomicLong THREAD_COUNT = new AtomicLong(0L);
    private static final ExecutorService EXECUTORS = new ThreadPoolExecutor(
            32 * 3, 32 * 3 * 2, 0, TimeUnit.MICROSECONDS,
            new LinkedBlockingQueue<>(256),
            runnable -> {
                Thread thread = new Thread(runnable);
                thread.setDaemon(false);
                thread.setName("work-thread-" + THREAD_COUNT.getAndIncrement());
                return thread;
            }
    );

    private Supplier<QueryUtils> queryUtilsSupplier = QueryUtils::new;

    public UserInfo convertUserInfo(UserInfo userInfo) {
        QuerySupplier carSupplier = new QuerySupplier(userInfo.getCarId(), "car", queryUtilsSupplier.get());

        CompletableFuture<String> getCarDesc = CompletableFuture
                .supplyAsync(carSupplier, EXECUTORS);

        getCarDesc.thenAccept((carDesc) -> userInfo.setCarDes(carDesc));

        QuerySupplier homeSupplier = new QuerySupplier(userInfo.getHomeId(), "home", queryUtilsSupplier.get());
        CompletableFuture<String> getHomeDesc = CompletableFuture.supplyAsync(homeSupplier, EXECUTORS);
        getHomeDesc.thenAccept(userInfo::setHomeDes);

        QuerySupplier jobSupplier = new QuerySupplier(userInfo.getJobId(), "job", queryUtilsSupplier.get());
        CompletableFuture<String> getJobDesc = CompletableFuture.supplyAsync(jobSupplier, EXECUTORS);
        getJobDesc.thenAccept(userInfo::setJobDes);

        // 描述任务链
        CompletableFuture<Void> getUserInfo = CompletableFuture.allOf(getCarDesc, getHomeDesc, getJobDesc)
                .thenAcceptAsync(aVoid -> System.out.println("全部查询完成！"));

        // 执行任务
        getUserInfo.join();

        return userInfo;
    }

    // 异步 API，IO阻塞
    public CompletableFuture<UserInfo> convertUserInfoAsync(UserInfo userInfo) {

        QuerySupplier carSupplier = new QuerySupplier(userInfo.getCarId(), "car", queryUtilsSupplier.get());
        QuerySupplier homeSupplier = new QuerySupplier(userInfo.getHomeId(), "home", queryUtilsSupplier.get());
        QuerySupplier jobSupplier = new QuerySupplier(userInfo.getJobId(), "job", queryUtilsSupplier.get());

        CompletableFuture<Void> carFuture = CompletableFuture.supplyAsync(carSupplier,EXECUTORS).thenAccept(userInfo::setCarDes);
        CompletableFuture<Void> homeFuture = CompletableFuture.supplyAsync(homeSupplier,EXECUTORS).thenAccept(userInfo::setHomeDes);
        CompletableFuture<Void> jobFuture = CompletableFuture.supplyAsync(jobSupplier,EXECUTORS).thenAccept(userInfo::setJobDes);

        return CompletableFuture.supplyAsync(() -> {
            CompletableFuture.allOf(carFuture, homeFuture, jobFuture).join();
            return userInfo;
        }, EXECUTORS);
    }
}
