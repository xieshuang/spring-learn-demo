package com.github.xsh.springshiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

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
                        @RequestParam String password,
                        HttpServletResponse response) {
        //对前端传过来的用户名密码进行进行解决处理 此处省略
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);
            response.setHeader("X-Frame-Options", "SAMEORIGIN");
            Map map = new HashMap();
            map.put("token", subject.getSession().getId().toString());
            return map;
        } catch (IncorrectCredentialsException e) {
            //密码错误
            return "ERROR_LOGIN_PASSWORD";
        } catch (ExcessiveAttemptsException e) {
            return "重试次数过多";
        } catch (LockedAccountException e) {
            //账号被禁用
            return "账号被禁用";
        } catch (AuthenticationException e) {
            //该用户不存在
            return "用户不存在";
        } catch (Exception e) {
            return "异常错误";
        }
    }
}
