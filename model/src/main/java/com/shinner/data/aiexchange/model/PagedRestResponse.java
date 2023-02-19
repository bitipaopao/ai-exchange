package com.shinner.data.aiexchange.model;

public class PagedRestResponse<T> extends RestResponse<T> {

    private ResponsePage page;

    public ResponsePage getPage() {
        return page;
    }

    public PagedRestResponse<T> setPage(ResponsePage page) {
        this.page = page;
        return this;
    }
}
