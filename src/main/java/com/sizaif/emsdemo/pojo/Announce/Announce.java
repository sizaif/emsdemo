package com.sizaif.emsdemo.pojo.Announce;

/**
 * @author ：sizaif
 * @date ：Created in 2020/4/25 21:02
 * @description：公告栏
 * @modified By：sizaif
 * @version: V1.0$
 */

public class Announce {
    private Integer id;
    private String memo;
    private Integer creatorId;
    private String creatorName;
    private String createDate;
    private String modifyDate;
    private Integer isEnabled;

    public Announce() {
    }

    public Announce(Integer isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Announce(Integer id, String memo, Integer creatorId, String creatorName, String createDate, String modifyDate, Integer isEnabled) {
        this.id = id;
        this.memo = memo;
        this.creatorId = creatorId;
        this.creatorName = creatorName;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.isEnabled = isEnabled;
    }

    public Integer getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Integer isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
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

    @Override
    public String toString() {
        return "Announce{" +
                "id=" + id +
                ", memo='" + memo + '\'' +
                ", creatorId=" + creatorId +
                ", creatorName='" + creatorName + '\'' +
                ", createDate='" + createDate + '\'' +
                ", modifyDate='" + modifyDate + '\'' +
                ", isEnabled=" + isEnabled +
                '}';
    }
}
