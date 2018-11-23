package com.cn.api.util;

/**
 * 接口定制，比如支持批量操作的MySqlMapper
 */

import tk.mybatis.mapper.common.Mapper;

public interface MyMapper<T> extends Mapper<T> {

}

