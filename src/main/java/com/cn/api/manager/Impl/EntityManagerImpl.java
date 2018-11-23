package com.cn.api.manager.Impl;

import com.cn.api.manager.EntityManager;
import com.cn.api.util.MyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntityManagerImpl<E> implements EntityManager<E>{

    @Autowired
    private MyMapper mapper;

    @Override
    public E find(E var1) {
        return (E) mapper.selectOne(var1);
    }

    @Override
    public int delete(E var1) {
        return mapper.delete(var1);
    }

    @Override
    public int insert(E var1) {
        return mapper.insertSelective(var1);
    }
}
