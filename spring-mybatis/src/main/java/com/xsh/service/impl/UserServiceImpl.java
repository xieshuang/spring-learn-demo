package com.xsh.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xsh.dao.UserMapper;
import com.xsh.pojo.db.User;
import com.xsh.service.IUserService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

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
        return userMapper.getUser(userName, password);
    }

    /**
     *
     * @param user
     * @param rowBounds
     * @return
     */
    @Override
    public PageInfo<User> getUserListByPage(User user, RowBounds rowBounds) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        if (user.getuId() != null) {
            criteria.andEqualTo("uId", user.getuId());
        }
        PageHelper.startPage(0,5);
        List<User> list = userMapper.selectByExample(example);
        return new PageInfo<User>(list);
    }
}
