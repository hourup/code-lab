package com.code.lab.changhr.concurrency.java8.cfuture.querydemo;

import java.util.concurrent.TimeUnit;

/**
 * @author changhr2013
 * @date 2020/4/5
 */
public class QueryUtils {

    public String queryCar(Integer carId) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        return "car_desc";
    }

    public String queryJob(Integer jobId) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        return "job_desc";
    }

    public String queryHome(Integer  homeId) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        return "home_desc";
    }
}
