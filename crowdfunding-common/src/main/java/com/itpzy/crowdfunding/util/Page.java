package com.itpzy.crowdfunding.util;

import java.util.List;

public class Page {

    //1.当前页
    private Integer pageNo;
    //2.每页显示的个数
    private Integer pageSize;
    //3.数据表查询的起始索引
    private Integer startIndex;
    //4.总记录数
    private Integer totalCount;
    //5.总页数
    private Integer totalPage;
    //6.查询的数据集合
    private List list;

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

    public Integer getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
