package com.company.core.domain;

/**
 * Created by fireWorks on 2016/2/26.
 */
public class BaseBO {
    private Integer pageCurrent;

    private Integer pageNo   = 1;

    private Integer pageSize = 20;

    public Integer getPageCurrent() {
        return pageCurrent;
    }

    public void setPageCurrent(Integer pageCurrent) {
        this.pageCurrent = pageCurrent;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}
