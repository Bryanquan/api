package com.cn.api.manager;

public interface EntityManager<E> {
    E find( E var1);

    int delete(E var1);

    int insert(E var1);
}
