package com.sizaif.emsdemo.pojo;

public class Contest {

    private int Cid;

    private int CreatorId;

    private int isEnabled;

    private String Name;

    private String Memo;

    private String Caption;

    private String CreateDate;

    private String ModifyDate;

    private String StartTime;

    private String EndTime;

    public Contest() {
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

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMemo() {
        return Memo;
    }

    public void setMemo(String memo) {
        Memo = memo;
    }

    public String getCaption() {
        return Caption;
    }

    public void setCaption(String caption) {
        Caption = caption;
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

    @Override
    public String toString() {
        return "Contest{" +
                "Cid=" + Cid +
                ", CreatorId=" + CreatorId +
                ", isEnabled=" + isEnabled +
                ", Name='" + Name + '\'' +
                ", Memo='" + Memo + '\'' +
                ", Caption='" + Caption + '\'' +
                ", CreateDate='" + CreateDate + '\'' +
                ", ModifyDate='" + ModifyDate + '\'' +
                ", StartTime='" + StartTime + '\'' +
                ", EndTime='" + EndTime + '\'' +
                '}';
    }
}
