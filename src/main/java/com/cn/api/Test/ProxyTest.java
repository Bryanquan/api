package com.cn.api.Test;

import com.cn.api.tools.*;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;


public class ProxyTest {
    /**
     * 静态代理
     */
    @Test
    public void testProxy(){
        UserManagerImpl userManager = new UserManagerImpl();
        UserManagerProxy proxy = new UserManagerProxy(userManager);
        proxy.addUser();
    }

    @Test
    public void testJdkProxy(){
        MobilePhone mp=new AndroidMobilePhone("杰克","23");
        InvocationHandler handler=new MobilePhoneHandler<MobilePhone>(mp);
        MobilePhone mpProxy=(MobilePhone)Proxy.newProxyInstance(MobilePhone.class.getClassLoader(),new Class<?>[]{MobilePhone.class},handler );
        mpProxy.callJack();

        UserManagerImpl userManager = new UserManagerImpl();
        InvocationHandler jdkProxyHandler = new JdkProxyHandler(userManager);
        UserManager proxy  =(UserManager) Proxy.newProxyInstance(UserManager.class.getClassLoader(),new Class<?>[]{UserManager.class},jdkProxyHandler);
        proxy.addUser();

        CGLibProxy cgLibProxy = new CGLibProxy();
        UserManager proxyObj = (UserManager)cgLibProxy.createProxyObject(userManager);
        proxyObj.addUser();
    }
}
