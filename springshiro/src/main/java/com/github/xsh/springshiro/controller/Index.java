package com.github.xsh.springshiro.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 首页
 * @date: 2019/6/12
 * @author: shuangxie@iflytek.com
 **/
@RestController
@RequestMapping("system")
public class Index {

    /**
     * 登录
     * @param username
     * @param password 
     * @author shuangxie
     * @date 2019/6/12
     */
    @RequestMapping("login")
    public Object login(@RequestParam String username,
                        @RequestParam String password) {
        //用户名 密码 解密处理等等

        return "success";
    }
}
