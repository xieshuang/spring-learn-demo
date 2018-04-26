package com.xsh.service;

import java.util.List;

/**
 * @Author:xieshuang
 * @Description: 通用service接口
 * @Date:Create in 16:16 2018/4/26
 * @Modified By :
 */
public interface IBaseService<T> {

    int deleteByPrimaryKey(Integer id);

    int insert(T record);

    T selectByPrimaryKey(Integer id);

    List<T> selectAll();

    int updateByPrimaryKey(T record);

}
