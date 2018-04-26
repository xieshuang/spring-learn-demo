package com.xsh.controller;

import com.xsh.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @Author:xieshuang
 * @Description:
 * @Date:Create in 16:29 2018/4/26
 * @Modified By :
 */
@Controller
public class BaseController {
    @Autowired
    IUserService userService;
}
