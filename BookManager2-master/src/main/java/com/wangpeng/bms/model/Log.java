package com.wangpeng.bms.model;

import java.io.Serializable;
import java.util.Date;

public class Log implements Serializable {
    private Integer logId;

    private Integer operatorId;

    private Date operateTime;

    private String operateContent;

    private String note;
    private String operatorName;
    private Integer isdel;

    public Integer getIsdel() {
        return isdel;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }


    private String operateTimestr;
    public String getOperateTimestr() {
        return operateTimestr;
    }

    public void setOperateTimestr(String operateTimestr) {
        this.operateTimestr = operateTimestr;
    }

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperateContent() {
        return operateContent;
    }

    public void setOperateContent(String operateContent) {
        this.operateContent = operateContent;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}