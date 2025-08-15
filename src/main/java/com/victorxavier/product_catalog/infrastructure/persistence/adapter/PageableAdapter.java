package com.victorxavier.product_catalog.infrastructure.persistence.adapter;

import com.victorxavier.product_catalog.domain.pagination.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class PageableAdapter {
    
    private final org.springframework.data.domain.Pageable springPageable;
    
    public PageableAdapter(org.springframework.data.domain.Pageable springPageable) {
        this.springPageable = springPageable;
    }
    
    public static org.springframework.data.domain.Pageable toSpring(Pageable pageable) {
        if (pageable.getSort() != null && !pageable.getSort().isEmpty()) {
            Sort.Direction direction = pageable.getSortDirection() == com.victorxavier.product_catalog.domain.pagination.Sort.Direction.DESC 
                ? Sort.Direction.DESC : Sort.Direction.ASC;
            Sort sort = Sort.by(direction, pageable.getSort());
            return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        }
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
    }
    
    public static Pageable fromSpring(org.springframework.data.domain.Pageable springPageable) {
        return new Pageable(springPageable.getPageNumber(), springPageable.getPageSize());
    }
}