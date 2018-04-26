package com.xsh.common.util;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @Author:xieshuang
 * @Description: 标记接口,集成通用Mapper,提供扩展性
 * @Date:Create in 16:09 2018/4/26
 * @Modified By :
 */
public interface EasyMapper<T> extends Mapper<T>, MySqlMapper<T>{
}
