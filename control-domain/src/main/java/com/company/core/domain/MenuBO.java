package com.company.core.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by APPLE on 16/1/12.
 */
public class MenuBO implements Comparable<MenuBO> {

    private String       funcId;

    private String       funcName;

    private String       funcFatherId;

    private String       funcDesc;

    private String       funcLevel;

    private String       funcUrl;

    private String       funcIcon;

    private BigDecimal   funcPriority;

    private List<MenuBO> childMenuBOList;

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

    public List<MenuBO> getChildMenuBOList() {
        return childMenuBOList;
    }

    public void setChildMenuBOList(List<MenuBO> childMenuBOList) {
        this.childMenuBOList = childMenuBOList;
    }

    public int compareTo(MenuBO o) {
        if (this.funcPriority.compareTo(o.funcPriority) != 0) {
            return this.funcPriority.compareTo(o.funcPriority);
        } else {
            return this.funcId.compareTo(o.funcId);
        }
    }
}
