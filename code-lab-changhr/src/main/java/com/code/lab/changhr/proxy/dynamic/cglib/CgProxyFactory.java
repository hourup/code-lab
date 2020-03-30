package com.code.lab.changhr.proxy.dynamic.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author changhr
 * @create 2019-10-09 10:46
 */
public class CgProxyFactory implements MethodInterceptor {

    private Object target;

    public CgProxyFactory(Object target) {
        this.target = target;
    }

    public Object getProxyInstance() {
        // 工具类
        Enhancer enhancer = new Enhancer();
        // 设置父类
        enhancer.setSuperclass(target.getClass());
        // 设置回调函数
        enhancer.setCallback(this);
        // 创建子类对象代理
        return enhancer.create();
    }

    /**
     * @param sub         cglib 生成的代理对象
     * @param method      被代理对象方法
     * @param args        方法入参
     * @param methodProxy 代理方法
     */
    @Override
    public Object intercept(Object sub, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("开启事务");
        // 执行目标对象的方法
        method.invoke(target, args);
        System.out.println("关闭事务");
        return null;
    }
}
