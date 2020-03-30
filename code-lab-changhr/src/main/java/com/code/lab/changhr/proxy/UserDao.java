package com.code.lab.changhr.proxy;

/**
 * @author changhr
 * @create 2019-10-09 10:04
 */
public class UserDao implements IUserDao {

    @Override
    public void save() {
        System.out.println(this.getClass().getName() + ": 保存数据");
    }
}
