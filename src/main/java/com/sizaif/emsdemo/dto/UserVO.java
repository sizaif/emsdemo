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
    private Boolean isEnabled;
    private Boolean isLocked;
    private String createDate;
    private String modifyDate;
    private String lastLoginDate;
    private String lastLogIp;
    private String lockDate;
    private String name;
    private String password;
    private String role;

    private List<UserRoleKey> userRoles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public Boolean getLocked() {
        return isLocked;
    }

    public void setLocked(Boolean locked) {
        isLocked = locked;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(String lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getLastLogIp() {
        return lastLogIp;
    }

    public void setLastLogIp(String lastLogIp) {
        this.lastLogIp = lastLogIp;
    }

    public String getLockDate() {
        return lockDate;
    }

    public void setLockDate(String lockDate) {
        this.lockDate = lockDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<UserRoleKey> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRoleKey> userRoles) {
        this.userRoles = userRoles;
    }

    @Override
    public String toString() {
        return "UserVO{" +
                "id=" + id +
                ", isEnabled=" + isEnabled +
                ", isLocked=" + isLocked +
                ", createDate='" + createDate + '\'' +
                ", modifyDate='" + modifyDate + '\'' +
                ", lastLoginDate='" + lastLoginDate + '\'' +
                ", lastLogIp='" + lastLogIp + '\'' +
                ", lockDate='" + lockDate + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", userRoles=" + userRoles +
                '}';
    }
}
