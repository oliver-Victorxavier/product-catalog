package com.victorxavier.product_catalog.domain.pagination;

import java.util.List;

public class Page<T> {

    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;

    public Page(List<T> content, int pageNumber, int pageSize, long totalElements) {
        this.content = content;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
    }

    public List<T> getContent() {

        return content;
    }

    public int getPageNumber() {

        return pageNumber;
    }

    public int getPageSize() {

        return pageSize;
    }

    public long getTotalElements() {

        return totalElements;
    }

    public int getTotalPages() {
        if (pageSize == 0) return 0;
        return (int) Math.ceil((double) totalElements / pageSize);
    }

    public boolean isFirst() {

        return pageNumber == 0;
    }

    public boolean isLast() {

        return pageNumber >= getTotalPages() - 1;
    }

}