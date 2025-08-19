package com.victorxavier.product_catalog.domain.pagination;

public class Pageable {
    private int pageNumber;
    private int pageSize;
    private String sort;
    private Sort.Direction sortDirection;
    
    
    public Pageable() {
        this.pageNumber = 0;
        this.pageSize = 20;
        this.sortDirection = Sort.Direction.ASC;
    }
    
    public Pageable(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sortDirection = Sort.Direction.ASC;
    }
    
    public Pageable(int pageNumber, int pageSize, String sort, Sort.Direction sortDirection) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sort = sort;
        this.sortDirection = sortDirection;
    }
    
    public int getPageNumber() {
        return pageNumber;
    }
    
    public int getPageSize() {
        return pageSize;
    }
    
    public String getSort() {
        return sort;
    }
    
    public Sort.Direction getSortDirection() {
        return sortDirection;
    }
    
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
    
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    
    public void setSort(String sort) {
        this.sort = sort;
    }
    
    public void setSortDirection(Sort.Direction sortDirection) {
        this.sortDirection = sortDirection;
    }
}