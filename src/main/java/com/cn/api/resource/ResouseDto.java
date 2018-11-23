package com.cn.api.resource;

public interface ResouseDto<T, D> {
    T createDto(D entity);
}
