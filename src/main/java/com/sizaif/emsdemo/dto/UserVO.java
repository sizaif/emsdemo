package com.sizaif.emsdemo.dto;

import com.sizaif.emsdemo.pojo.User.UserRoleKey;

import java.util.List;

/**
 * @author ：sizaif
 * @date ：Created in 2020/4/17 23:07
 * @description：用户角色
 * @modified By：sizaif
 * @version: v1.0$
 */

public class UserVO {
    private int id;
    private int isEnabled;
    private int isLocked;
    private String createDate;
    private String modifyDate;
    private String lastLoginDate;
    private String lastLogIp;
    private String lockDate;
    private String name;
    private String password;
    private String role;

    private List<UserRoleKey> userRoles;


}
