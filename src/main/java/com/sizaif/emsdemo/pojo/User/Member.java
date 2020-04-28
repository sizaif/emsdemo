package com.sizaif.emsdemo.pojo.User;

import com.sizaif.emsdemo.dto.UserVO;

public class Member {

    private Integer id;
    private Integer gender;
    private Integer age;

    private Integer isEnabled;

    private String height;
    private String weight;

    private String address;
    private String birth;
    private String email;
    private String phone;
    private String truename;
    private String school;
    private String image;

    public Member() {
    }

    public Member(Integer id, Integer gender, Integer age, Integer isEnabled, String height, String weight, String address, String birth, String email, String phone, String truename, String school, String image) {
        this.id = id;
        this.gender = gender;
        this.age = age;
        this.isEnabled = isEnabled;
        this.height = height;
        this.weight = weight;
        this.address = address;
        this.birth = birth;
        this.email = email;
        this.phone = phone;
        this.truename = truename;
        this.school = school;
        this.image = image;
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

    public Integer getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Integer isEnabled) {
        this.isEnabled = isEnabled;
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

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", gender=" + gender +
                ", age=" + age +
                ", isEnabled=" + isEnabled +
                ", height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                ", address='" + address + '\'' +
                ", birth='" + birth + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", truename='" + truename + '\'' +
                ", school='" + school + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
