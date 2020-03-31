package com.code.lab.changhr.concurrency.java8.cfuture;

import com.code.lab.changhr.concurrency.utils.Delay;

/**
 * 模拟折扣服务
 *
 * @author changhr
 * @create 2020-03-31 15:25
 */
public class Discount {

    public enum Code {
        // 折扣力度枚举
        NONE(0),
        SILVER(5),
        GOLD(10),
        PLATINUM(15),
        DIAMOND(20),
        ;

        private final int percentage;

        Code(int percentage) {
            this.percentage = percentage;
        }
    }

    /**
     * 模拟计算折扣价格的 API 服务
     */
    public static String applyDiscount(Quote quote) {
        return quote.getShopName() + " price is "
                + Discount.apply(quote.getPrice(), quote.getDiscountCode());
    }

    private static double apply(double price, Code code) {
        Integer delay = Delay.delay(1000);
        System.out.println("apply discount delay time is " + delay + " ms.");
        return price * (100 - code.percentage) / 100;
    }

}
