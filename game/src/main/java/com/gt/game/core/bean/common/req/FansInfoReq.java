package com.gt.game.core.bean.common.req;

/**
 * Created by zwq on 2018/1/25 0025.
 *
 */
public class FansInfoReq {

    private Integer busId;//商家id

    private Integer curPage;//当前页

    private Integer pageSize;

    private String search;//关键字查询

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getBusId() {
        return busId;
    }

    public void setBusId(Integer busId) {
        this.busId = busId;
    }

    public Integer getCurPage() {
        return curPage;
    }

    public void setCurPage(Integer curPage) {
        this.curPage = curPage;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
