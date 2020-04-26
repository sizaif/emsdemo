package com.sizaif.emsdemo.dto;

import com.sizaif.emsdemo.pojo.Contest.ContestMemberkey;
import com.sizaif.emsdemo.pojo.Contest.ContestTeamKey;
import com.sizaif.emsdemo.pojo.User.Member;
import com.sizaif.emsdemo.pojo.Contest.Team;

import java.util.List;

/**
 * @author ：sizaif
 * @date ：Created in 2020/4/14 22:58
 * @description：赛事以及赛事成员表赛事队伍表
 * @modified By：sizaif
 * @version: 1.0$
 */

public class ContestVO {

    private int Cid;

    private int CreatorId;

    private int isEnabled;

    private String Title;

    private String Memo;

    private String Length;

    private String Level;

    private String CreateDate;

    private String ModifyDate;

    private String StartTime;

    private String EndTime;

    private String Type;

    private String Tag;

    private List<String> tags;

    private List<MemberVO> memberList;

    private List<TeamVO> teamList;


    public ContestVO() {
    }

    public ContestVO(int cid, int creatorId, int isEnabled, String title, String memo, String length, String level, String createDate, String modifyDate, String startTime, String endTime, String type, String tag, List<String> tags, List<MemberVO> memberList, List<TeamVO> teamList) {
        Cid = cid;
        CreatorId = creatorId;
        this.isEnabled = isEnabled;
        Title = title;
        Memo = memo;
        Length = length;
        Level = level;
        CreateDate = createDate;
        ModifyDate = modifyDate;
        StartTime = startTime;
        EndTime = endTime;
        Type = type;
        Tag = tag;
        this.tags = tags;
        this.memberList = memberList;
        this.teamList = teamList;
    }

    public int getCid() {
        return Cid;
    }

    public void setCid(int cid) {
        Cid = cid;
    }

    public int getCreatorId() {
        return CreatorId;
    }

    public void setCreatorId(int creatorId) {
        CreatorId = creatorId;
    }

    public int getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(int isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getMemo() {
        return Memo;
    }

    public void setMemo(String memo) {
        Memo = memo;
    }

    public String getLength() {
        return Length;
    }

    public void setLength(String length) {
        Length = length;
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String level) {
        Level = level;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getModifyDate() {
        return ModifyDate;
    }

    public void setModifyDate(String modifyDate) {
        ModifyDate = modifyDate;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<MemberVO> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<MemberVO> memberList) {
        this.memberList = memberList;
    }

    public List<TeamVO> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<TeamVO> teamList) {
        this.teamList = teamList;
    }

    @Override
    public String toString() {
        return "ContestVO{" +
                "Cid=" + Cid +
                ", CreatorId=" + CreatorId +
                ", isEnabled=" + isEnabled +
                ", Title='" + Title + '\'' +
                ", Memo='" + Memo + '\'' +
                ", Length='" + Length + '\'' +
                ", Level='" + Level + '\'' +
                ", CreateDate='" + CreateDate + '\'' +
                ", ModifyDate='" + ModifyDate + '\'' +
                ", StartTime='" + StartTime + '\'' +
                ", EndTime='" + EndTime + '\'' +
                ", Type='" + Type + '\'' +
                ", Tag='" + Tag + '\'' +
                ", tags=" + tags +
                ", memberList=" + memberList +
                ", teamList=" + teamList +
                '}';
    }
}
