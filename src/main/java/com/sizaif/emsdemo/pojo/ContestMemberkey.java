package com.sizaif.emsdemo.pojo;

/**
 * @author ：sizaif
 * @date ：Created in 2020/4/14 20:50
 * @description：赛事用户关系表
 * @modified By：sizaif
 * @version: v1.1$
 */

public class ContestMemberkey {

    private Integer contestId;
    private Integer memberId;
    private Integer isEnabled;

    public Integer getContestId() {
        return contestId;
    }

    public void setContestId(Integer contestId) {
        this.contestId = contestId;
    }

    public Integer getmemberId() {
        return memberId;
    }

    public void setmemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Integer isEnabled) {
        this.isEnabled = isEnabled;
    }

    @Override
    public String toString() {
        return "ContestMemberkey{" +
                "contestId=" + contestId +
                ", memberId=" + memberId +
                ", isEnabled=" + isEnabled +
                '}';
    }
}
