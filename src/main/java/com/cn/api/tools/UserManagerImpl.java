package com.cn.api.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserManagerImpl implements UserManager {
    private static final Logger logger = LoggerFactory.getLogger(UserManagerImpl.class);

    @Override
    public int addUser() {
        logger.info("---正在新增User--");
        return 1;
    }
}
