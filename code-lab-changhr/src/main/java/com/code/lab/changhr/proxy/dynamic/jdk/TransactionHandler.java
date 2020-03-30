package com.code.lab.changhr.proxy.dynamic.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author changhr
 * @create 2019-10-09 10:15
 */
public class TransactionHandler implements InvocationHandler {

    private Object target;

    public TransactionHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("开启事务");
        // 执行目标对象方法
        method.invoke(target, args);
        System.out.println("提交事务");
        return null;
    }
}
