package com.code.lab.changhr.proxy.dynamic.cglib;

import com.code.lab.changhr.proxy.IUserDao;
import com.code.lab.changhr.proxy.UserDao;
import com.code.lab.changhr.proxy.UserDaoNoInf;

/**
 * cglib 动态代理测试
 *
 * @author changhr
 * @create 2019-10-09 11:02
 */
public class CglibProxyTest {

    public static void main(String[] args) {

        IUserDao target = new UserDao();
        System.out.println(target.getClass());
        IUserDao proxy = (IUserDao) new CgProxyFactory(target).getProxyInstance();
        System.out.println(proxy.getClass());
        proxy.save();

        System.out.println("========== ==========");

        UserDaoNoInf userDaoNoInf = new UserDaoNoInf();
        System.out.println(userDaoNoInf);
        UserDaoNoInf proxyUserDaoNoInf = (UserDaoNoInf) new CgProxyFactory(userDaoNoInf).getProxyInstance();
        System.out.println(proxyUserDaoNoInf.getClass());
        proxyUserDaoNoInf.save();
    }
}