package com.code.lab.changhr.concurrency.java8.cfuture.javaaction;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author changhr
 * @create 2020-03-31 15:34
 */
@Getter
@AllArgsConstructor
public class Quote {

    /** shop 名称 */
    private final String shopName;

    /** 折扣之前的价格 */
    private final double price;

    /** 折扣码 */
    private final Discount.Code discountCode;

    public static Quote parse(String s) {
        String[] split = s.split(":");
        String shopName = split[0];
        double price = Double.parseDouble(split[1]);
        Discount.Code discountCode = Discount.Code.valueOf(split[2]);
        return new Quote(shopName, price, discountCode);
    }
}
