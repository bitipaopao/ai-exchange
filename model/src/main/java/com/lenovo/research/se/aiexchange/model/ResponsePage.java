package com.lenovo.research.se.aiexchange.model;

@SuppressWarnings("unused")
public class ResponsePage {

    private Integer totalCount;
    private Integer pageCount;
    private Integer currentPage;
    private Integer perPage;
    private Integer pageSize;

    private static final int DEFAULT_PER_PAGE = 50;
    private static final int DEFAULT_PAGE = 1;

    public ResponsePage() {
    }

    public ResponsePage(Integer totalCount, Integer currentPage, Integer perPage) {
        super();
        this.totalCount = totalCount;
        if (currentPage == null) {
            this.currentPage = DEFAULT_PAGE;
        } else {
            this.currentPage = currentPage;
        }
        if (perPage == null) {
            this.perPage = DEFAULT_PER_PAGE;
        } else {
            this.perPage = perPage;
        }
        if (totalCount != null && perPage != null) {
            this.pageCount = totalCount/perPage + 1;
        }
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public ResponsePage setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public ResponsePage setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
        return this;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public ResponsePage setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
        return this;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public ResponsePage setPerPage(Integer perPage) {
        this.perPage = perPage;
        return this;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public ResponsePage setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }
}
