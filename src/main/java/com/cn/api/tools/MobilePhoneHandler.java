package com.cn.api.tools;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class  MobilePhoneHandler<T> implements InvocationHandler {
    private T target;

    public MobilePhoneHandler(T target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        //前置处理
        System.out.println("--动态代理前置处理--");
        Object obj=method.invoke(target,args);
        //后置处理
        System.out.println("--动态代理后置处理--");
        return obj;
    }
}
