package com.github.xsh.springshiro.model;

import java.io.Serializable;

/**
 * @description: 用户实体
 * @date: 2019/6/12
 * @author: shuangxie@iflytek.com
 **/
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 是否启用 true 启用
     */
    private boolean enabled;

    public User(){

    }

    public User(String username) {
        this.username = username;
    }

    /**
     * 获取 用户名
     *
     * @return username 用户名
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * 设置 用户名
     *
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取 密码
     *
     * @return password 密码
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * 设置 密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取 是否启用
     *
     * @return enabled 是否启用
     */
    public boolean getEnabled() {
        return this.enabled;
    }

    /**
     * 设置 是否启用
     *
     * @param enabled 是否启用
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
