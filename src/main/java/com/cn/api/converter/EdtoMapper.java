package com.cn.api.converter;

import com.cn.api.manager.EntityManager;

/**
 * entity dto之间映射接口
 * @param <E>
 * @param <D>
 */
public interface EdtoMapper<E, D> {

    D map(EntityManager var1, Object var2);

    E unmap(D var1);
}
