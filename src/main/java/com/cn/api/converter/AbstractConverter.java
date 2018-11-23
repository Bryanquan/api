package com.cn.api.converter;

public abstract class  AbstractConverter<E, D> implements EdtoMapper<E, D> {
    Class<?> getEntityClass(E e){
        return e.getClass();
    }

    Class<?> getDtoEntityClass(D e){
        return e.getClass();
    }
}
