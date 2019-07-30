package com.cn.api.tools;


import org.apache.log4j.Logger;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class JdkProxyHandler<T> implements InvocationHandler {
    private static final Logger logger = Logger.getLogger(JdkProxyHandler.class);
    private T target;

    public JdkProxyHandler(T object ){
        this.target = object;

    }
    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        logger.info(o.getClass());
        logger.info("---JDK动态代理前处理--");
        Object returnValue = method.invoke(target,objects);//必须是目标对象
        logger.info("---JDK动态代理后处理");
        return returnValue;
    }
}
