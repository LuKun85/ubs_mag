package com.company.core.entity;

public class TBLSYSPARAMDO {
    private String paramCode;

    private Long version;

    private String paramValue;

    private String paramCheck;

    private String paramRule;

    private String paramModifyFlag;

    private String paramDesc;

    private String paramRemark;

    private String lastUpdateOrg;

    private String lastUpdateAcc;

    private String lastUpdateTime;

    private String reserved1;

    private String reserved2;

    public String getParamCode() {
        return paramCode;
    }

    public void setParamCode(String paramCode) {
        this.paramCode = paramCode == null ? null : paramCode.trim();
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue == null ? null : paramValue.trim();
    }

    public String getParamCheck() {
        return paramCheck;
    }

    public void setParamCheck(String paramCheck) {
        this.paramCheck = paramCheck == null ? null : paramCheck.trim();
    }

    public String getParamRule() {
        return paramRule;
    }

    public void setParamRule(String paramRule) {
        this.paramRule = paramRule == null ? null : paramRule.trim();
    }

    public String getParamModifyFlag() {
        return paramModifyFlag;
    }

    public void setParamModifyFlag(String paramModifyFlag) {
        this.paramModifyFlag = paramModifyFlag == null ? null : paramModifyFlag.trim();
    }

    public String getParamDesc() {
        return paramDesc;
    }

    public void setParamDesc(String paramDesc) {
        this.paramDesc = paramDesc == null ? null : paramDesc.trim();
    }

    public String getParamRemark() {
        return paramRemark;
    }

    public void setParamRemark(String paramRemark) {
        this.paramRemark = paramRemark == null ? null : paramRemark.trim();
    }

    public String getLastUpdateOrg() {
        return lastUpdateOrg;
    }

    public void setLastUpdateOrg(String lastUpdateOrg) {
        this.lastUpdateOrg = lastUpdateOrg == null ? null : lastUpdateOrg.trim();
    }

    public String getLastUpdateAcc() {
        return lastUpdateAcc;
    }

    public void setLastUpdateAcc(String lastUpdateAcc) {
        this.lastUpdateAcc = lastUpdateAcc == null ? null : lastUpdateAcc.trim();
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime == null ? null : lastUpdateTime.trim();
    }

    public String getReserved1() {
        return reserved1;
    }

    public void setReserved1(String reserved1) {
        this.reserved1 = reserved1 == null ? null : reserved1.trim();
    }

    public String getReserved2() {
        return reserved2;
    }

    public void setReserved2(String reserved2) {
        this.reserved2 = reserved2 == null ? null : reserved2.trim();
    }
}