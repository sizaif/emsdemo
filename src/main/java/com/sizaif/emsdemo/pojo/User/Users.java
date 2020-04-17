package com.sizaif.emsdemo.pojo.User;

import java.io.Serializable;

public class Users implements Serializable {

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

    public Users() {
    }

    public Users(int id, int isEnabled, int isLocked, String createDate, String modifyDate, String lastLoginDate, String lastLogIp, String lockDate, String name, String password, String role) {
        this.id = id;
        this.isEnabled = isEnabled;
        this.isLocked = isLocked;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.lastLoginDate = lastLoginDate;
        this.lastLogIp = lastLogIp;
        this.lockDate = lockDate;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(int isEnabled) {
        this.isEnabled = isEnabled;
    }

    public int getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(int isLocked) {
        this.isLocked = isLocked;
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

    @Override
    public String toString() {
        return "Users{" +
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
                '}';
    }
}
