package com.sizaif.emsdemo.pojo.User;

import java.io.Serializable;

public class Users implements Serializable {

    private int id;
    private Boolean isEnabled;
    private Boolean isLocked;
    private String createDate;
    private String modifyDate;
    private String lastLoginDate;
    private String lastLoginIp;
    private String lockDate;
    private String name;
    private String password;
    private String role;

    //账号类型
    private String type;

    public Users() {
    }

    public Users(int id, Boolean isEnabled, Boolean isLocked, String createDate, String modifyDate, String lastLoginDate, String lastLoginIp, String lockDate, String name, String password, String role, String type) {
        this.id = id;
        this.isEnabled = isEnabled;
        this.isLocked = isLocked;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.lastLoginDate = lastLoginDate;
        this.lastLoginIp = lastLoginIp;
        this.lockDate = lockDate;
        this.name = name;
        this.password = password;
        this.role = role;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
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

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", isEnabled=" + isEnabled +
                ", isLocked=" + isLocked +
                ", createDate='" + createDate + '\'' +
                ", modifyDate='" + modifyDate + '\'' +
                ", lastLoginDate='" + lastLoginDate + '\'' +
                ", lastLoginIp='" + lastLoginIp + '\'' +
                ", lockDate='" + lockDate + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
