package com.github.xsh.springshiro.config.web;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 登出过滤器--禁用重定向
 * @date: 2019/06/12
 * @author: shuangxie
 **/
public class SystemLogoutFilter extends LogoutFilter {
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        //在这里执行退出系统前需要清空的数据
        Subject subject = getSubject(request, response);
        subject.logout();
        //返回false表示不执行后续的过滤器，直接返回跳转到登录页面
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        httpServletResponse.getWriter().write("success");
        return false;

    }
}
