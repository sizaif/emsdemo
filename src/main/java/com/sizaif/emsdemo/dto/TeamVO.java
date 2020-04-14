package com.sizaif.emsdemo.dto;

import com.sizaif.emsdemo.pojo.Member;

import java.util.List;

/**
 * @author ：sizaif
 * @date ：Created in 2020/4/14 21:54
 * @description：队伍成员信息表队伍包含成员信息)
 * @modified By：sizaif
 * @version: 1.0$
 */

public class TeamVO {
    private int id;

    private String name;

    private String school;

    private int captainId;

    private String createDate;

    private String modifyDate;

    private int isEnabled;

    // 队伍下的成员信息
    private List<Member> teamMember;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public int getCaptainId() {
        return captainId;
    }

    public void setCaptainId(int captainId) {
        this.captainId = captainId;
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

    public List<Member> getTeamMember() {
        return teamMember;
    }

    public void setTeamMember(List<Member> teamMember) {
        this.teamMember = teamMember;
    }

    public int getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(int isEnabled) {
        this.isEnabled = isEnabled;
    }

    @Override
    public String toString() {
        return "TeamVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", school='" + school + '\'' +
                ", captainId=" + captainId +
                ", createDate='" + createDate + '\'' +
                ", modifyDate='" + modifyDate + '\'' +
                ", isEnabled=" + isEnabled +
                ", teamMember=" + teamMember +
                '}';
    }
}
