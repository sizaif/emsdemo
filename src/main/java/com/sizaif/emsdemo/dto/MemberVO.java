package com.sizaif.emsdemo.dto;

import java.util.List;

/**
 * @author ：sizaif
 * @date ：Created in 2020/4/19 14:16
 * @description：关联用户成员角色表信息
 * @modified By：sizaif
 * @version: v1.0$
 */

public class MemberVO {

    private int id;
    private int memberRankId;
    private String address;
    private String birth;
    private String email;
    private int gender;
    private String phone;
    private String truename;
    private String school;
    private String image;

    // 可以存放 member 是否启用 也可以存放 参加某个赛事时 是否报名成功
    private boolean isEnabled;

    private UserVO users;

    public MemberVO() {
    }

    public MemberVO(int id, int memberRankId, String address, String birth, String email, int gender, String phone, String truename, String school, String image, boolean isEnabled, UserVO users) {
        this.id = id;
        this.memberRankId = memberRankId;
        this.address = address;
        this.birth = birth;
        this.email = email;
        this.gender = gender;
        this.phone = phone;
        this.truename = truename;
        this.school = school;
        this.image = image;
        this.isEnabled = isEnabled;
        this.users = users;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMemberRankId() {
        return memberRankId;
    }

    public void setMemberRankId(int memberRankId) {
        this.memberRankId = memberRankId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public UserVO getUsers() {
        return users;
    }

    public void setUsers(UserVO users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "MemberVO{" +
                "id=" + id +
                ", memberRankId=" + memberRankId +
                ", address='" + address + '\'' +
                ", birth='" + birth + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                ", phone='" + phone + '\'' +
                ", truename='" + truename + '\'' +
                ", school='" + school + '\'' +
                ", image='" + image + '\'' +
                ", users=" + users +
                '}';
    }
}
