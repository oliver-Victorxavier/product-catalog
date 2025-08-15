package com.victorxavier.product_catalog.domain.pagination;

public interface Pageable {
    int getPageNumber();
    int getPageSize();
    Sort getSort();
}