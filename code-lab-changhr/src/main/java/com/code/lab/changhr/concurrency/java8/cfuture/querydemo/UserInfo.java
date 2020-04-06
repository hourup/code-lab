package com.code.lab.changhr.concurrency.java8.cfuture.querydemo;


import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户信息
 *
 * @author changhr2013
 * @date 2020/4/5
 */
@Data
@Accessors(chain = true)
public class UserInfo {

    private Integer id;

    private String name;

    private Integer jobId;

    private String jobDes;

    private Integer carId;

    private String carDes;

    private Integer homeId;

    private String homeDes;
}
