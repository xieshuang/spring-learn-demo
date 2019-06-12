package com.github.xsh.springshiro.config.web;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 自定义拦截过滤 解决前后端分离中存在的问题
 *
 * @date: 2019/06/12
 * @author: shuangxie
 **/
public class AuthenticationFilter extends FormAuthenticationFilter {

    /**
     *  解决shiro重定向问题
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
            throws Exception {
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        httpServletResponse.setStatus(HttpStatus.OK.value());
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "token");
        //解决低危漏洞点击劫持 X-Frame-Options Header未配置
        httpServletResponse.setHeader("X-Frame-Options", "SAMEORIGIN");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");

        httpServletResponse.getWriter().write("用户未登录");
        return false;
    }

    /**
     *  重写方法解决shiro prelight request问题
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (request instanceof HttpServletRequest && ((HttpServletRequest) request).getMethod().equalsIgnoreCase("OPTIONS")) {
            return true;
        }
        return super.isAccessAllowed(request, response, mappedValue);
    }
}
