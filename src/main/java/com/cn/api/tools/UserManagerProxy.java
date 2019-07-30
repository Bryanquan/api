package com.cn.api.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserManagerProxy  implements UserManager{
   private static final Logger logger = LoggerFactory.getLogger(UserManagerProxy.class);

   private UserManager userManager;

   public UserManagerProxy(UserManager userManager){
       this.userManager = userManager;
   }
    @Override
    public int addUser() {
        logger.info("---静态代理前置处理---");
        userManager.addUser();
        logger.info("---静态代理后置处理---");
        return 1;
    }
}
