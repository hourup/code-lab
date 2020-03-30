package com.code.lab.changhr.proxy.statc;

import com.code.lab.changhr.proxy.IUserDao;

/**
 * 静态代理对象
 *
 * @author changhr
 * @create 2019-10-09 10:05
 */
public class UserDaoProxy implements IUserDao {

    private IUserDao target;

    public UserDaoProxy(IUserDao target) {
        this.target = target;
    }

    @Override
    public void save() {
        System.out.println("开启事务");
        target.save();
        System.out.println("提交事务");
    }
}
