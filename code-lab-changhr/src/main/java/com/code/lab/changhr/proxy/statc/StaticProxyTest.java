package com.code.lab.changhr.proxy.statc;

import com.code.lab.changhr.proxy.IUserDao;
import com.code.lab.changhr.proxy.UserDao;

/**
 * 静态代理测试
 *
 * @author changhr
 * @create 2019-10-09 10:10
 */
public class StaticProxyTest {

    public static void main(String[] args) {
        IUserDao target = new UserDao();

        final UserDaoProxy proxy = new UserDaoProxy(target);
        proxy.save();
    }
}
