package com.code.lab.changhr.concurrency.java8.cfuture.javaaction;

import cn.hutool.core.util.RandomUtil;
import com.code.lab.changhr.concurrency.utils.Delay;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * 商店类
 *
 * @author changhr
 * @create 2020-03-30 17:21
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Shop {

    private String name;

    public Future<Double> getPriceAsyncManual(String product) {
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();

        new Thread(() -> {
            try {
                double price = calculatePrice(product);
                futurePrice.complete(price);
            } catch (Exception e) {
                futurePrice.completeExceptionally(e);
            }
        }).start();

        return futurePrice;
    }

    /**
     * 使用工厂方法 supplyAsync 创建 CompletableFuture 对象
     *
     * 此方法返回的 CompletableFuture 对象和 getPriceAsyncManual() 方法手工创建的 CompletableFuture 对象是完全等价的，
     * 这意味着它提供了同样的错误管理机制，而前者构建需要花费大量精力。
     *
     */
    public Future<Double> getPriceAsync(String product) {
        return CompletableFuture.supplyAsync(() -> calculatePrice(product));
    }

    /**
     * 模拟计算价格的同步 API 接口
     */
    public double getPrice(String product) {
        return calculatePrice(product);
    }

    /** 模拟计算价格的 API，返回折扣码 */
    public String getPriceWithDiscountCode(String product) {
        double price = calculatePrice(product);

        Discount.Code code = Discount.Code.values()[RandomUtil.randomInt(Discount.Code.values().length)];

        return String.format("%s:%.2f:%s", name, price, code);
    }

    private double calculatePrice(String product) {
        // 在 getPrice 方法中引入一个模拟的延迟
        Integer delay = Delay.delay(1000);
        System.out.println("calculate price delay time is " + delay + " ms.");
        return RandomUtil.randomDouble() + product.charAt(0) + product.charAt(1);
    }
}
