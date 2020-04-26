package com.sizaif.emsdemo.dto;

import com.sizaif.emsdemo.pojo.User.Member;

import java.util.List;

/**
 * @author ：sizaif
 * @date ：Created in 2020/4/14 21:54
 * @description：队伍成员信息表队伍包含成员信息)
 * @modified By：sizaif
 * @version: 1.0$
 */

public class TeamVO {
    private Integer id;

    private String name;

    private String school;

    private Integer captainId;

    private String createDate;

    private String modifyDate;

    private Integer isEnabled;

    // 队伍下的成员信息
    private List<MemberVO> teamMember;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getCaptainId() {
        return captainId;
    }

    public void setCaptainId(Integer captainId) {
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

    public Integer getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Integer isEnabled) {
        this.isEnabled = isEnabled;
    }

    public List<MemberVO> getTeamMember() {
        return teamMember;
    }

    public void setTeamMember(List<MemberVO> teamMember) {
        this.teamMember = teamMember;
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
