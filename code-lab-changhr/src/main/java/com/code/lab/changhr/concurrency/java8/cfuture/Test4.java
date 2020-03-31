package com.code.lab.changhr.concurrency.java8.cfuture;

import java.util.concurrent.CompletableFuture;

/**
 * @author changhr
 * @create 2020-03-31 17:21
 */
public class Test4 {

    public static void main(String[] args) {

        Shop shop = new Shop("BestPrice");
        String product = "iPhone";

        CompletableFuture<Quote> quoteFuture = CompletableFuture.supplyAsync(() -> shop.getPriceWithDiscountCode(product))
                .thenApply(Quote::parse);

        Quote quote = quoteFuture.join();

        CompletableFuture<String> discount1 = CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote));
        CompletableFuture<String> discount2 = CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote));
        CompletableFuture<String> discount3 = CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote));
        CompletableFuture<String> discount4 = CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote));

    }

}
