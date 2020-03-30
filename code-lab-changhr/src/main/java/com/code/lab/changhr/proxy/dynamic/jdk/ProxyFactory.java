package com.code.lab.changhr.proxy.dynamic.jdk;

import java.lang.reflect.Proxy;

/**
 * @author changhr
 * @create 2019-10-09 10:12
 */
public class ProxyFactory {

    private Object target;

    public ProxyFactory(Object target) {
        this.target = target;
    }

    // 为目标对象生成代理对象
    public Object getProxyInstance() {
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new TransactionHandler(target));
    }
}
