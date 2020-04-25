package com.sizaif.emsdemo.dto;

import com.sizaif.emsdemo.pojo.Contest.ContestMemberkey;
import com.sizaif.emsdemo.pojo.Contest.ContestTeamKey;
import com.sizaif.emsdemo.pojo.Contest.Team;
import com.sizaif.emsdemo.pojo.User.Member;

import java.util.List;

/**
 * @author ：sizaif
 * @date ：Created in 2020/4/25 14:14
 * @description：contest member team
 * @modified By：sizaif
 * @version: v1.0$
 */

public class ContestVO2 {
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

    private Member memberList;

    private Team teamList;

    private ContestMemberkey cmk;

    private ContestTeamKey ctk;

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

    public Member getMemberList() {
        return memberList;
    }

    public void setMemberList(Member memberList) {
        this.memberList = memberList;
    }

    public Team getTeamList() {
        return teamList;
    }

    public void setTeamList(Team teamList) {
        this.teamList = teamList;
    }

    public ContestMemberkey getCmk() {
        return cmk;
    }

    public void setCmk(ContestMemberkey cmk) {
        this.cmk = cmk;
    }

    public ContestTeamKey getCtk() {
        return ctk;
    }

    public void setCtk(ContestTeamKey ctk) {
        this.ctk = ctk;
    }

    @Override
    public String toString() {
        return "ContestVO2{" +
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
                ", cmk=" + cmk +
                ", ctk=" + ctk +
                '}';
    }
}
