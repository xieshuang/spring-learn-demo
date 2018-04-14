/**
 * TestController.java
 * Created at 2017-8-1
 * Created by xieshuang
 * Copyright (C) 2017 BROADTEXT SOFTWARE, All rights reserved.
 */
package com.xsh.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xsh.model.UserInfo;
import com.xsh.service.UserService;

/**
 * <p>ClassName: TestController</p>
 * <p>Description: TODO</p>
 * <p>Author: xieshuang</p>
 * <p>Date: 2017-8-1</p>
 */
@Controller
@RequestMapping("/testController")
public class TestController {
 
	private Logger log = Logger.getLogger(TestController.class); 
	
	@Autowired
	private UserService userService;
	
	
	@RequestMapping("/getView")
	@ResponseBody
	public ModelAndView test(){
		ModelAndView view = new ModelAndView("test");
		List<UserInfo> userInfos = userService.getUsers();
		view.addObject("users", userInfos);
		log.info("访问成功");
		return view;
	}
	
}
