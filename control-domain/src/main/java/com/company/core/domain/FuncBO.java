package com.company.core.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by fireWorks on 2016/2/26.
 */
public class FuncBO extends BaseBO {
    private String     funcId;

    private String     funcName;

    private String     funcFatherId;

    private String     funcDesc;

    private String     funcRemark;

    private String     funcDisableTag;

    private String     funcCreateBy;

    private Date       funcCreateDatetime;

    private String     funcUpdateBy;

    private Date       funcUpdateDatetime;

    private String     funcTag;

    private String     funcLevel;

    private String     funcUrl;

    private String     funcIcon;

    private BigDecimal funcPriority;

    private String     checked;

    public String getFuncId() {
        return funcId;
    }

    public void setFuncId(String funcId) {
        this.funcId = funcId;
    }

    public String getFuncName() {
        return funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    public String getFuncFatherId() {
        return funcFatherId;
    }

    public void setFuncFatherId(String funcFatherId) {
        this.funcFatherId = funcFatherId;
    }

    public String getFuncDesc() {
        return funcDesc;
    }

    public void setFuncDesc(String funcDesc) {
        this.funcDesc = funcDesc;
    }

    public String getFuncRemark() {
        return funcRemark;
    }

    public void setFuncRemark(String funcRemark) {
        this.funcRemark = funcRemark;
    }

    public String getFuncDisableTag() {
        return funcDisableTag;
    }

    public void setFuncDisableTag(String funcDisableTag) {
        this.funcDisableTag = funcDisableTag;
    }

    public String getFuncCreateBy() {
        return funcCreateBy;
    }

    public void setFuncCreateBy(String funcCreateBy) {
        this.funcCreateBy = funcCreateBy;
    }

    public Date getFuncCreateDatetime() {
        return funcCreateDatetime;
    }

    public void setFuncCreateDatetime(Date funcCreateDatetime) {
        this.funcCreateDatetime = funcCreateDatetime;
    }

    public String getFuncUpdateBy() {
        return funcUpdateBy;
    }

    public void setFuncUpdateBy(String funcUpdateBy) {
        this.funcUpdateBy = funcUpdateBy;
    }

    public Date getFuncUpdateDatetime() {
        return funcUpdateDatetime;
    }

    public void setFuncUpdateDatetime(Date funcUpdateDatetime) {
        this.funcUpdateDatetime = funcUpdateDatetime;
    }

    public String getFuncTag() {
        return funcTag;
    }

    public void setFuncTag(String funcTag) {
        this.funcTag = funcTag;
    }

    public String getFuncLevel() {
        return funcLevel;
    }

    public void setFuncLevel(String funcLevel) {
        this.funcLevel = funcLevel;
    }

    public String getFuncUrl() {
        return funcUrl;
    }

    public void setFuncUrl(String funcUrl) {
        this.funcUrl = funcUrl;
    }

    public String getFuncIcon() {
        return funcIcon;
    }

    public void setFuncIcon(String funcIcon) {
        this.funcIcon = funcIcon;
    }

    public BigDecimal getFuncPriority() {
        return funcPriority;
    }

    public void setFuncPriority(BigDecimal funcPriority) {
        this.funcPriority = funcPriority;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked == null ? null : checked.trim();
    }
}
