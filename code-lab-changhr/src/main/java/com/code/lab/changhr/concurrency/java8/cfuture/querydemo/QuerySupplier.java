package com.code.lab.changhr.concurrency.java8.cfuture.querydemo;

import lombok.AllArgsConstructor;

import java.util.function.Supplier;

/**
 * @author changhr2013
 * @date 2020/4/5
 */
@AllArgsConstructor
public class QuerySupplier implements Supplier<String> {

    private Integer id;
    private String type;
    private QueryUtils queryUtils;

    @Override
    public String get() {
        switch (type) {
            case "home":
                return queryUtils.queryHome(id);
            case "job":
                return queryUtils.queryJob(id);
            case "car":
                return queryUtils.queryCar(id);
            default:
                return null;
        }
    }
}
