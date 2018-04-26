package com.xsh.service.impl;

import com.xsh.dao.UserMapper;
import com.xsh.pojo.db.User;
import com.xsh.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author:xieshuang
 * @Description:
 * @Date:Create in 16:23 2018/4/26
 * @Modified By :
 */
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements IUserService {

    @Autowired
    UserMapper userMapper;

    /**
     * 根据用户名和密码获取用户信息
     * @param userName
     * @param password
     * @return
     */
    @Override
    public User getUser(String userName, String password) {
        return userMapper.getUser(userName,password);
    }
}
