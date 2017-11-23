package com.company.core.form;


public class BaseForm<T> {

    private String pageSize = "10";

    private String pageNo = "1";

    private String pageCurrent = "1";

    Pagination<T> pagination;

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getPageCurrent() {
        return pageCurrent;
    }

    public void setPageCurrent(String pageCurrent) {
        this.pageCurrent = pageCurrent;
    }

    public Pagination<T> getPagination() {
        return pagination;
    }

    public void setPagination(Pagination<T> pagination) {
        this.pagination = pagination;
    }
}

