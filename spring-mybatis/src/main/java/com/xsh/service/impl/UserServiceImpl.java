package com.xsh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xsh.dao.UserInfoMapper;
import com.xsh.model.UserInfo;
import com.xsh.service.UserService;

/**
 * 创建时间：2015-1-27 下午5:22:59
 * 
 * @author andy
 * @version 2.2
 */
@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserInfoMapper userInfoMapper;


	@Override
	public List<UserInfo> getUsers() {
		return userInfoMapper.selectAll();
	}

}
