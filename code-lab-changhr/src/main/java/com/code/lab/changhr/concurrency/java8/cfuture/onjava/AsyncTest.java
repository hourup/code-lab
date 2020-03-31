package com.code.lab.changhr.concurrency.java8.cfuture.onjava;

import org.apache.commons.lang3.time.StopWatch;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author changhr2013
 * @date 2020/3/31
 */
public class AsyncTest {

    public static void main(String[] args) {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        CompletableFuture<MaterialStore.Eggs> eggsFuture = CompletableFuture.supplyAsync(MaterialStore.Eggs::new);
        CompletableFuture<MaterialStore.Milk> milkFuture = CompletableFuture.supplyAsync(MaterialStore.Milk::new);
        CompletableFuture<MaterialStore.Sugar> sugarFuture = CompletableFuture.supplyAsync(MaterialStore.Sugar::new);
        CompletableFuture<MaterialStore.Flour> flourFuture = CompletableFuture.supplyAsync(MaterialStore.Flour::new);

        MaterialStore.Eggs eggs = eggsFuture.join();
        MaterialStore.Milk milk = milkFuture.join();
        MaterialStore.Sugar sugar = sugarFuture.join();
        MaterialStore.Flour flour = flourFuture.join();

        CompletableFuture<MaterialStore.FrostCake> frostCakeFuture = CompletableFuture
                .supplyAsync(() -> new MaterialStore.Batter(eggs, milk, sugar, flour))
                .thenApplyAsync(MaterialStore.Baked::new)
                .thenCombineAsync(
                        CompletableFuture.supplyAsync(MaterialStore.Frosting::new),
                        (baked, frosting) -> new MaterialStore.FrostCake(frosting, baked)
                );

        MaterialStore.FrostCake frostCake = frostCakeFuture.join();

        stopWatch.stop();
        System.out.println("总耗时：" + stopWatch.getTime(TimeUnit.MILLISECONDS) + "ms");
    }
}
