package com.victorxavier.product_catalog.domain.entity;

import com.victorxavier.product_catalog.domain.projection.ProductProjection;
import java.util.List;

public class ProductPage {
    private List<ProductProjection> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;

    public ProductPage(List<ProductProjection> content, int pageNumber, int pageSize, long totalElements, int totalPages) {
        this.content = content;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public List<ProductProjection> getContent() {

        return content;
    }

    public void setContent(List<ProductProjection> content) {

        this.content = content;
    }

    public int getPageNumber() {

        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {

        this.pageNumber = pageNumber;
    }

    public int getPageSize() {

        return pageSize;
    }

    public void setPageSize(int pageSize) {

        this.pageSize = pageSize;
    }

    public long getTotalElements() {

        return totalElements;
    }

    public void setTotalElements(long totalElements) {

        this.totalElements = totalElements;
    }

    public int getTotalPages() {

        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
