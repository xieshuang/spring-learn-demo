package com.github.xsh.springshiro.config.shiro;

import com.github.xsh.springshiro.config.web.AuthenticationFilter;
import com.github.xsh.springshiro.config.web.SystemLogoutFilter;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.IRedisManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSentinelManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {


    /**
     * 环境变量
     */
    @Autowired
    private Environment env;

    @Value("${spring.redis.timeout}")
    private int timeout;
    @Value("${server.servlet.session.timeout}")
    private int sessionTimeout;
    @Value("${spring.redis.database}")
    private int db;
    @Value("${spring.profiles.active}")
    private String profile;

    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //注意过滤器配置顺序 不能颠倒
        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/system/login", "anon");
        filterChainDefinitionMap.put("/**", "authc");
        //未授权界面
        //配置shiro默认登录界面地址，前后端分离中登录界面跳转应由前端路由控制，后台仅返回json数据
        shiroFilterFactoryBean.getFilters().put("authc", new AuthenticationFilter());
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了，登出后跳转配置的loginUrl
        shiroFilterFactoryBean.getFilters().put("logout", new SystemLogoutFilter());
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 凭证匹配器
     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     * ）
     * @return
     */
    @Bean
    public RetryLimitHashedCredentialsMatcher hashedCredentialsMatcher() {
        RetryLimitHashedCredentialsMatcher hashedCredentialsMatcher = new RetryLimitHashedCredentialsMatcher();
        //散列算法:这里使用MD5算法
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        //散列的次数
        hashedCredentialsMatcher.setHashIterations(2);
        return hashedCredentialsMatcher;
    }

    @Bean
    public ShiroRealm myShiroRealm() {
        ShiroRealm myShiroRealm = new ShiroRealm();
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myShiroRealm;
    }


    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        // 自定义session管理 使用redis
        securityManager.setSessionManager(sessionManager());
        // 自定义缓存实现 使用redis
        securityManager.setCacheManager(cacheManager());
        return securityManager;
    }

    //自定义sessionManager
    @Bean
    public org.apache.shiro.session.mgt.SessionManager sessionManager() {
        SessionManager mySessionManager = new SessionManager();
        mySessionManager.setSessionDAO(redisSessionDAO());
        return mySessionManager;
    }


    /**
     * cacheManager 缓存 redis实现
     * <p>
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    @Bean
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        redisCacheManager.setKeyPrefix("dwuser:shiro:cache:");
        //从插件源码中可以看出能够指定key 这里设置用户名为key
        redisCacheManager.setPrincipalIdFieldName("username");
        return redisCacheManager;
    }

    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * <p>
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
//      Custom your redis key prefix for session management, if you doesn't define this parameter,
//      shiro-redis will use 'shiro_redis_session:' as default prefix
        //session key
        redisSessionDAO.setKeyPrefix("dwuser:shiro:token:");
        //session超时时间
        redisSessionDAO.setExpire(sessionTimeout);
        return redisSessionDAO;
    }
    /**
     * 配置shiro redisManager
     * 从源码看出支持单机模式和哨兵模式 该插件jar中没有做到像spring-boot自动实例化对应的class
     * <p>
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    public IRedisManager redisManager() {
        if (profile.equals("dev") || profile.equals("test")) {
            //开发环境---单机模式
            RedisManager redisManager = new RedisManager();
            redisManager.setHost(env.getProperty("spring.redis.host") + ":" + env.getProperty("spring.redis.port"));
            redisManager.setTimeout(timeout);
//            redisManager.setPassword(password);
            redisManager.setDatabase(db);
            return redisManager;
        } else {
            //生产 主从模式
            RedisSentinelManager redisSentinelManager = new RedisSentinelManager();
            redisSentinelManager.setMasterName(env.getProperty("spring.redis.sentinel.master"));
            redisSentinelManager.setHost(env.getProperty("spring.redis.sentinel.nodes"));
//            redisSentinelManager.setPassword(password);
            redisSentinelManager.setDatabase(db);
            return redisSentinelManager;
        }
    }



    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
