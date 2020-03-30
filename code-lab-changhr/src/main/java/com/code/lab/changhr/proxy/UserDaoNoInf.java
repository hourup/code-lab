package com.code.lab.changhr.proxy;

/**
 * @author changhr
 * @create 2020-03-30 17:00
 */
public class UserDaoNoInf {

    public void save() {
        System.out.println(this.getClass().getName() + ": 保存数据");
    }
}
