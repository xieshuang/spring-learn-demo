package com.xsh.service;

import com.github.pagehelper.PageInfo;
import com.xsh.pojo.db.User;
import org.apache.ibatis.session.RowBounds;

/**
 * @Author:xieshuang
 * @Description:
 * @Date:Create in 16:23 2018/4/26
 * @Modified By :
 */
public interface IUserService extends IBaseService<User>{
    /**
     * 根据用户名和密码获取用户信息
     * @param userName
     * @param password
     * @return
     */
    User getUser(String userName, String password);

    /**
     * 分页查询
     * @param user
     * @param rowBounds
     * @return
     */
    PageInfo<User> getUserListByPage(User user, RowBounds rowBounds);
}
