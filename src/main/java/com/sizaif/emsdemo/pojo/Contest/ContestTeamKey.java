package com.sizaif.emsdemo.pojo.Contest;

/**
 * @author ：sizaif
 * @date ：Created in 2020/4/14 23:09
 * @description：赛事队伍关系表
 * @modified By：sizaif
 * @version: v1.0$
 */

public class ContestTeamKey {
    private Integer contestId;
    private Integer teamId;
    private Integer isEnabled;


    public ContestTeamKey() {
    }

    public ContestTeamKey(Integer contestId, Integer teamId, Integer isEnabled) {
        this.contestId = contestId;
        this.teamId = teamId;
        this.isEnabled = isEnabled;
    }

    public Integer getContestId() {
        return contestId;
    }

    public void setContestId(Integer contestId) {
        this.contestId = contestId;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Integer getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Integer isEnabled) {
        this.isEnabled = isEnabled;
    }

    @Override
    public String toString() {
        return "ContestTeamKey{" +
                "contestId=" + contestId +
                ", teamId=" + teamId +
                ", isEnabled=" + isEnabled +
                '}';
    }
}
