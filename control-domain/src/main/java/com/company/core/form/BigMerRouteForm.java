package com.company.core.form;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by fireWorks on 2016/3/8.
 */
public class BigMerRouteForm {

    private String bigMerId;

    private String bigMerName;

    private BigDecimal dayAmtLimit;

    private BigDecimal monthAmtLimit;

//    private BigDecimal currentDayAmt;

//    private BigDecimal currentMouthAmt;

    private String openTimeStart;

    private String openTimeEnd;

    private String openFlag;

//    private String safeTime;

    private String safeTimeFlag;

//    private Date lastSendTime;

    public String getBigMerId() {
        return bigMerId;
    }

    public void setBigMerId(String bigMerId) {
        this.bigMerId = bigMerId == null ? null : bigMerId.trim();
    }

    public BigDecimal getDayAmtLimit() {
        return dayAmtLimit;
    }

    public void setDayAmtLimit(BigDecimal dayAmtLimit) {
        this.dayAmtLimit = dayAmtLimit;
    }

    public BigDecimal getMonthAmtLimit() {
        return monthAmtLimit;
    }

    public void setMonthAmtLimit(BigDecimal monthAmtLimit) {
        this.monthAmtLimit = monthAmtLimit;
    }

//    public BigDecimal getCurrentDayAmt() {
//        return currentDayAmt;
//    }
//
//    public void setCurrentDayAmt(BigDecimal currentDayAmt) {
//        this.currentDayAmt = currentDayAmt;
//    }
//
//    public BigDecimal getCurrentMouthAmt() {
//        return currentMouthAmt;
//    }
//
//    public void setCurrentMouthAmt(BigDecimal currentMouthAmt) {
//        this.currentMouthAmt = currentMouthAmt;
//    }

    public String getOpenTimeStart() {
        return openTimeStart;
    }

    public void setOpenTimeStart(String openTimeStart) {
        this.openTimeStart = openTimeStart == null ? null : openTimeStart.trim();
    }

    public String getOpenTimeEnd() {
        return openTimeEnd;
    }

    public void setOpenTimeEnd(String openTimeEnd) {
        this.openTimeEnd = openTimeEnd == null ? null : openTimeEnd.trim();
    }

    public String getOpenFlag() {
        return openFlag;
    }

    public void setOpenFlag(String openFlag) {
        this.openFlag = openFlag == null ? null : openFlag.trim();
    }

//    public String getSafeTime() {
//        return safeTime;
//    }
//
//    public void setSafeTime(String safeTime) {
//        this.safeTime = safeTime == null ? null : safeTime.trim();
//    }

    public String getSafeTimeFlag() {
        return safeTimeFlag;
    }

    public void setSafeTimeFlag(String safeTimeFlag) {
        this.safeTimeFlag = safeTimeFlag == null ? null : safeTimeFlag.trim();
    }

//    public Date getLastSendTime() {
//        return lastSendTime;
//    }
//
//    public void setLastSendTime(Date lastSendTime) {
//        this.lastSendTime = lastSendTime;
//    }

    public String getBigMerName() {
        return bigMerName;
    }

    public void setBigMerName(String bigMerName) {
        this.bigMerName = bigMerName == null ? null : bigMerName.trim();
    }
}
