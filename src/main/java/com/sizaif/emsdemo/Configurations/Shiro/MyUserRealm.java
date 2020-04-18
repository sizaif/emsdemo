package com.sizaif.emsdemo.Configurations.Shiro;

import com.sizaif.emsdemo.mapper.User.PermissionsMapper;
import com.sizaif.emsdemo.pojo.User.Permission;
import com.sizaif.emsdemo.pojo.User.Role;
import com.sizaif.emsdemo.pojo.User.Users;
import com.sizaif.emsdemo.service.Auth.AuthService;
import com.sizaif.emsdemo.service.User.UsersService;
import com.sizaif.emsdemo.service.User.impl.UsersServiceimpl;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;

public class MyUserRealm extends AuthorizingRealm{
    private static final Logger logger = LoggerFactory
            .getLogger(UsersServiceimpl.class);

    @Autowired
    AuthService authService;

    @Autowired
    UsersService usersService;
    @Autowired
    PermissionsMapper permissionsMapper;
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        logger.debug("执行了授权-->doGetAuthorizationInfo ");
        logger.debug("授予角色和权限 -> ");

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        // 拿到当前用户
        Subject subject = SecurityUtils.getSubject();
        Users currentUsers = (Users) subject.getPrincipal();

        /**
         *  授权:info.addStringPermissions;
         *  根据当前用户的id 查询用户的角色
         *  根据角色查询权限
         */
        Integer userId = currentUsers.getId();
        List<Role> roles = authService.getRoleByUser(userId);

        if (null != roles && roles.size() > 0){
            for (Role role : roles) {
                // 添加角色
                info.addRole(role.getCode());
                logger.debug(role.getCode());
                // 角色对应的权限数据
                List<Permission> perms = authService.findPermsByRoleId(role.getId());

                if (null != perms && perms.size() > 0) {
                    // 授权角色下所有权限
                    for (Permission perm : perms) {
                        info.addStringPermission(perm.getCode());
                    }
                }

            }
        }

        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken Token) throws AuthenticationException {

        System.out.println("执行了认证-->doGetAuthenticationInfo");

        UsernamePasswordToken userToken = (UsernamePasswordToken) Token;

        logger.info("用户登录认证：验证当前Subject时获取到token为：" + ReflectionToStringBuilder
                .toString(userToken, ToStringStyle.MULTI_LINE_STYLE));


        // 连接数据库
        Users users = usersService.queryUserByName(userToken.getUsername());
        logger.debug("用户登录认证！用户信息user：" + users);
        //是否有这个账户
        if(users==null){
            //没有这个人
            return null;
        }
        //是否激活
        if(users!=null && users.getEnabled()!=true  ){
            //未激活
            throw new DisabledAccountException();
        }
        //是否锁定
        if(users!=null && users.getLocked() == true){
            // 被锁定
            throw  new LockedAccountException();
        }
        // 密码认证
        // MD5 盐值加密:
        /**
         *  盐值为: name+id;
         *  md5(密码+盐值)
         */
        ByteSource credentialsSalt = ByteSource.Util.bytes(users.getName()+users.getId());
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
                users, //用户
                users.getPassword(), // 密码
                credentialsSalt,// salt = name + id
                getName() //realm name
        );
        return  simpleAuthenticationInfo;
    }

}
