package com.company.core.form;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页工具类
 */
public class Pagination<T> {
	
	//数据总数
    final private int     itemCount;
    //起始数据序号
    final private int     startIndex;
    //结束数据序号
    final private int     endIndex;
    //页号
    final private int     pageIndex;
    //每页大小
    final private int     pageSize;
    //前一页页号
    final private int     previousPage;
    //下一页页号
    final private int     nextPage;
    //总页数
    final private int     pageCount;
    //分页数据
    final private List<T> list;
    
    
    /**
     * 构造一个分页对象
     * 用于计算分页相关数据
     * @param itemCount 数据总数
     * @param pageIndex 页号
     * @param pageSize 每页大小
     */
    public Pagination(int itemCount, int pageIndex, int pageSize) {
        //数据总数
        this.itemCount = itemCount;
        //起始数据序号
        this.startIndex = (pageIndex - 1) * pageSize;
        //结束数据序号
        this.endIndex = (pageIndex) * pageSize + 1;
        //页号
        this.pageIndex = pageIndex;
        //每页大小
        this.pageSize = pageSize;
        //总页数
        this.pageCount = (int) (Math.ceil((double) itemCount / (double) pageSize));
        //前一页页号
        this.previousPage = pageIndex > 1 ? pageIndex - 1 : -1;
        //下一页页号
        this.nextPage = pageIndex < this.getPageCount() ? this.getPageIndex() + 1 : -1;
        //分页数据
        this.list = new ArrayList<T>(itemCount);
    }

    /**
     * 将查询结果放回分页对象
     *
     * @param resultList
     */
    public void addResult(List<T> resultList) {
        this.list.addAll(resultList);
    }


    public int getItemCount() {
        return itemCount;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPreviousPage() {
        return previousPage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public int getPageCount() {
        return pageCount;
    }

    public List<T> getList() {
        return list;
    }
}