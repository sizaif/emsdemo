package com.sizaif.emsdemo.dto;

import java.util.List;

/**
 * @author ：sizaif
 * @date ：Created in 2020/4/29 0:55
 * @description：成绩表组合
 * @modified By：sizaif
 * @version: v1.0$
 */

public class RankVO {


    // 用户ID
    private Integer mid;
    // 组队id
    private Integer tid;
    // 赛事名称
    private String ctitile;
    // 用户姓名
    private String mtruename;
    //组队名称
    private String tname;
    // 用户学校
    private String mschool;
    // 组队学校
    private String tschool;
    // AC数量
    private Integer ac_count;
    // 用时时间
    private Integer time_count;
    // 组队教练ID
    private Integer tteacherid;
    // 组队教练信息
    private MemberVO tteacherVO;
    // 组队 成员信息
    private List<MemberVO> tmemberList;


    public RankVO() {
    }

    public RankVO(Integer mid, Integer tid, String ctitile, String mtruename, String tname, String mschool, String tschool, Integer ac_count, Integer time_count, Integer tteacherid, MemberVO tteacherVO, List<MemberVO> tmemberList) {
        this.mid = mid;
        this.tid = tid;
        this.ctitile = ctitile;
        this.mtruename = mtruename;
        this.tname = tname;
        this.mschool = mschool;
        this.tschool = tschool;
        this.ac_count = ac_count;
        this.time_count = time_count;
        this.tteacherid = tteacherid;
        this.tteacherVO = tteacherVO;
        this.tmemberList = tmemberList;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getCtitile() {
        return ctitile;
    }

    public void setCtitile(String ctitile) {
        this.ctitile = ctitile;
    }

    public String getMtruename() {
        return mtruename;
    }

    public void setMtruename(String mtruename) {
        this.mtruename = mtruename;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getMschool() {
        return mschool;
    }

    public void setMschool(String mschool) {
        this.mschool = mschool;
    }

    public String getTschool() {
        return tschool;
    }

    public void setTschool(String tschool) {
        this.tschool = tschool;
    }

    public Integer getAc_count() {
        return ac_count;
    }

    public void setAc_count(Integer ac_count) {
        this.ac_count = ac_count;
    }

    public Integer getTime_count() {
        return time_count;
    }

    public void setTime_count(Integer time_count) {
        this.time_count = time_count;
    }

    public Integer getTteacherid() {
        return tteacherid;
    }

    public void setTteacherid(Integer tteacherid) {
        this.tteacherid = tteacherid;
    }

    public MemberVO getTteacherVO() {
        return tteacherVO;
    }

    public void setTteacherVO(MemberVO tteacherVO) {
        this.tteacherVO = tteacherVO;
    }

    public List<MemberVO> getTmemberList() {
        return tmemberList;
    }

    public void setTmemberList(List<MemberVO> tmemberList) {
        this.tmemberList = tmemberList;
    }

    @Override
    public String toString() {
        return "RankVO{" +
                "mid=" + mid +
                ", tid=" + tid +
                ", ctitile='" + ctitile + '\'' +
                ", mtruename='" + mtruename + '\'' +
                ", tname='" + tname + '\'' +
                ", mschool='" + mschool + '\'' +
                ", tschool='" + tschool + '\'' +
                ", ac_count=" + ac_count +
                ", time_count=" + time_count +
                ", tteacherid=" + tteacherid +
                ", tteacherVO=" + tteacherVO +
                ", tmemberList=" + tmemberList +
                '}';
    }
}
