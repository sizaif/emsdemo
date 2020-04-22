package com.sizaif.emsdemo.pojo.Contest;

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
    private boolean isEnabled;

    public ContestMemberkey() {
    }

    public ContestMemberkey(Integer contestId, Integer memberId, boolean isEnabled) {
        this.contestId = contestId;
        this.memberId = memberId;
        this.isEnabled = isEnabled;
    }

    public Integer getContestId() {
        return contestId;
    }

    public void setContestId(Integer contestId) {
        this.contestId = contestId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
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
