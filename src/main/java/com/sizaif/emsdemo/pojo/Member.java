package com.sizaif.emsdemo.pojo;

public class Member {

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

    private Users users;
    public Member() {
    }

    public Member(int id, int memberRankId, String address, String birth, String email, int gender, String phone, String truename, String school, String image) {
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
    }

    public Member(int id, int memberRankId, String address, String birth, String email, int gender, String phone, String truename, String school, String image, Users users) {
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
        this.users = users;
    }

    @Override
    public String toString() {
        return "Member{" +
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

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
