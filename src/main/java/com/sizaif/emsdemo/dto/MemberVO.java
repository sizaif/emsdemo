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

    private Integer id;
    private Integer gender;
    private Integer age;

    private String height;
    private String weight;

    private String address;
    private String birth;
    private String email;
    private String phone;
    private String truename;
    private String school;
    private String image;

    // 可以存放 member 是否启用 也可以存放 参加某个赛事时 是否报名成功
    private Integer isEnabled;

    private UserVO users;

    public MemberVO() {
    }

    public MemberVO(Integer id, Integer gender, Integer age, String height, String weight, String address, String birth, String email, String phone, String truename, String school, String image, Integer isEnabled, UserVO users) {
        this.id = id;
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.address = address;
        this.birth = birth;
        this.email = email;
        this.phone = phone;
        this.truename = truename;
        this.school = school;
        this.image = image;
        this.isEnabled = isEnabled;
        this.users = users;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
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

    public Integer getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Integer isEnabled) {
        this.isEnabled = isEnabled;
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
                ", gender=" + gender +
                ", age=" + age +
                ", height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                ", address='" + address + '\'' +
                ", birth='" + birth + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", truename='" + truename + '\'' +
                ", school='" + school + '\'' +
                ", image='" + image + '\'' +
                ", isEnabled=" + isEnabled +
                ", users=" + users +
                '}';
    }
}
