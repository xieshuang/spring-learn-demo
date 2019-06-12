package com.github.xsh.springshiro.config;

import com.github.xsh.springshiro.model.User;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义权限匹配和账号密码匹配
 */
public class ShiroRealm extends AuthorizingRealm {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

    /**
     * 该方法主要用来校验当前用户权限信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User userInfo = (User) principals.getPrimaryPrincipal();
        try {
            //这个根据用户信息从db或者property中获取对应的角色信息加上，这里mock数据示例
            authorizationInfo.addRole("super_admin");
        } catch (Exception e) {
            logger.error("shiro认证出错：", e);
        }
        return authorizationInfo;
    }

    /**
     * 身份认证--校验用户名、密码是否正确，账号是否禁用等等
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
    {
        //这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        String username = (String)token.getPrincipal();
        //根据用户名从db获取用户信息 这里用mock数据
        User userInfo = new User(username);
        userInfo.setPassword("123456");
        userInfo.setEnabled(true);
        if (!userInfo.getEnabled()) { //账户冻结
            throw new LockedAccountException();
        }
        return new SimpleAuthenticationInfo(
                userInfo, //用户名
                userInfo.getPassword(), //密码
                ByteSource.Util.bytes(userInfo.getUsername()),//盐值
                getName()  //realm name
        );
    }

}
