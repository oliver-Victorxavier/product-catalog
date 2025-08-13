package com.victorxavier.product_catalog.domain.pagination;


public class PageRequest {

    private int pageNumber;
    private int pageSize;
    private String sortField;
    private String sortDirection; // ASC or DESC

    public PageRequest(int pageNumber, int pageSize, String sortField, String sortDirection) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sortField = sortField;
        this.sortDirection = sortDirection;
    }

    public int getPageNumber() {

        return pageNumber;
    }

    public int getPageSize() {

        return pageSize;
    }

    public String getSortField() {

        return sortField;
    }

    public String getSortDirection() {
        return sortDirection;
    }

}