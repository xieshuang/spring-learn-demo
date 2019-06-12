package com.github.xsh.springshiro.config.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @description: 限制登录次数凭证器
 * @date: 2019/06/12
 * @author: shuangxie
 **/
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

    public static final String DEFAULT_RETRYLIMIT_CACHE_KEY_PREFIX = "dwuser:shiro:cache:retrylimit:";
    private String keyPrefix = DEFAULT_RETRYLIMIT_CACHE_KEY_PREFIX;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private String getRedisKickoutKey(String username) {
        return this.keyPrefix + username;
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        //获取用户名
        String username = (String)token.getPrincipal();
        //获取用户登录次数
        String count = redisTemplate.opsForValue().get(getRedisKickoutKey(username));
        Integer retryCount = Integer.parseInt(count == null? "0":count);
        if (retryCount == null) {
            //如果用户没有登陆过,登陆次数加1 并放入缓存
            retryCount = 0;
        }
        if (retryCount > 5) {
            //如果用户登陆失败次数大于5次 抛出锁定用户异常  并修改数据库字段
            //抛出异常
            throw new ExcessiveAttemptsException();
        }
        //判断用户账号和密码是否正确
        boolean matches = super.doCredentialsMatch(token, info);
        if (matches) {
            //如果正确,从缓存中将用户登录计数 清除
            redisTemplate.delete(getRedisKickoutKey(username));
        } else {
            retryCount = retryCount+1;
            redisTemplate.opsForValue().set(getRedisKickoutKey(username), String.valueOf(retryCount));
            redisTemplate.expire(getRedisKickoutKey(username), 10, TimeUnit.MINUTES);
        }
        return matches;
    }
}
