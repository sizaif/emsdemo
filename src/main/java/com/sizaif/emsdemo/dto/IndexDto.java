package com.sizaif.emsdemo.dto;

/**
 * 导航栏所需要的信息
 */
public class IndexDto {


    private int uid;
    // 用户名
    private String uName;
    // 用户昵称
    private String uNickName;
    // 头像
    private String uImage;
    // 密码
    private String passWord;
    // 用户是否登录
    private boolean isLogin;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuNickName() {
        return uNickName;
    }

    public void setuNickName(String uNickName) {
        this.uNickName = uNickName;
    }

    public String getuImage() {
        return uImage;
    }

    public void setuImage(String uImage) {
        this.uImage = uImage;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }
}
