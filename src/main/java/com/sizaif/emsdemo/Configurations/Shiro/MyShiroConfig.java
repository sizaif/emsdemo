package com.sizaif.emsdemo.Configurations.Shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class MyShiroConfig {

    private static final Logger logger = LoggerFactory
            .getLogger(MyShiroConfig.class);
    //ShiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("SecurityManager") DefaultWebSecurityManager defaultWebSecurityManager){

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 关联DefautWebaSecurityManager
        // 设置安全管理器
        /**
         anon（匿名）  org.apache.shiro.web.filter.authc.AnonymousFilter
         authc（身份验证）      org.apache.shiro.web.filter.authc.FormAuthenticationFilter
         authcBasic（http基本验证）   org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter
         logout（退出）       org.apache.shiro.web.filter.authc.LogoutFilter
         noSessionCreation（不创建session） org.apache.shiro.web.filter.session.NoSessionCreationFilter
         perms(许可验证) org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter
         port（端口验证） org.apache.shiro.web.filter.authz.PortFilter
         rest  (rest方面) org.apache.shiro.web.filter.authz.HttpMethodPermissionFilter
         roles（权限验证） org.apache.shiro.web.filter.authz.RolesAuthorizationFilter
         ssl （ssl方面） org.apache.shiro.web.filter.authz.SslFilter
         user （用户方面） org.apache.shiro.web.filter.authc.UserFilter
         */
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);


        // 指定要求登录时的链接
        shiroFilterFactoryBean.setLoginUrl("/toLogin");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/home");
        // 未授权时跳转的界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/error_401");

        // filterChainDefinitions拦截器=map必须用：LinkedHashMap，因为它必须保证有序
        Map<String, String> filterMap = new LinkedHashMap<>();

        // 注销
        filterMap.put("/logout","logout");

        // 配置记住我或认证通过可以访问的地址



        //设定拦截 以及权限
//        filterMap.put("/toAddPage","authc,perms[user:add]");


        // 个人信息界面需要要  profilePage 权限
        filterMap.put("/users/toProfilesPage/*","authc,perms[profilePage]");

//        filterMap.put("/contest/toContestList","authc,perms[contest:select]");
//        filterMap.put("/contest/toAddContestPage","authc,perms[contest:add]");

        filterMap.put("/users/*","authc");
        filterMap.put("/contest/*","authc");






        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        logger.debug("----------------->  Shiro拦截器工厂类注入成功");

        return  shiroFilterFactoryBean;
    }

    //DefautWebaSecurityManager
    @Bean(name = "SecurityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(@Qualifier("myUserRealm") MyUserRealm myUserRealm){

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        // 关联MyUserRealm
        securityManager.setRealm(myUserRealm);
        return  securityManager;
    }


    //Create realm Object
    @Bean(name = "myUserRealm")
    public MyUserRealm myUserRealm(){
        return new MyUserRealm();
    }


    /**
     * ShiroDialect，为了在thymeleaf里使用shiro的标签的bean
     * 整合shiroDialect  整合shiro 和thymeleaf
     * @return
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    /**
     * 配置cookie记住我管理器
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager() {
        logger.debug("配置cookie记住我管理器！");
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(remeberMeCookie());
        return cookieRememberMeManager;
    }

    /**
     * 设置记住我cookie过期时间
     * @return
     */
    @Bean
    public SimpleCookie remeberMeCookie() {
        logger.debug("记住我，设置cookie过期时间！");
        // cookie名称;对应前端的checkbox的name = rememberMe
        SimpleCookie scookie = new SimpleCookie("rememberMe");
        // 记住我cookie生效时间30天 ,单位秒 [1小时]
        scookie.setMaxAge(3600);
        return scookie;
    }
}
