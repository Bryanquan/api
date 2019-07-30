package com.cn.api.tools;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CGLibProxy implements MethodInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(CGLibProxy.class);

    // CGlib需要代理的目标对象
    private Object targetObject;

    public Object createProxyObject(Object obj) {
        this.targetObject = obj;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(obj.getClass());
        enhancer.setCallback(this);
        Object proxyObj = enhancer.create();
        return proxyObj;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

        Object obj = null;
        // 过滤方法
        if ("addUser".equals(method.getName())) {
              logger.info("--动态代理执行--");
        }
        logger.info("--cglib动态代理--");
        obj = method.invoke(targetObject, args);
        logger.info("--新增User结束--");
        return obj;
    }
}
