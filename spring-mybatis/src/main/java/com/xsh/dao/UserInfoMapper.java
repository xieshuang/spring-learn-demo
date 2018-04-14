package com.xsh.dao;

import java.util.List;

import com.xsh.model.UserInfo;

public interface UserInfoMapper {
    
    List<UserInfo> selectAll();
}