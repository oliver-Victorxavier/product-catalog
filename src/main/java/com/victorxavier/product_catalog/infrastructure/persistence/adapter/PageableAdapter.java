package com.victorxavier.product_catalog.infrastructure.persistence.adapter;

import com.victorxavier.product_catalog.domain.pagination.Pageable;
import com.victorxavier.product_catalog.domain.pagination.Sort;

public class PageableAdapter implements Pageable {
    
    private final org.springframework.data.domain.Pageable springPageable;
    
    public PageableAdapter(org.springframework.data.domain.Pageable springPageable) {
        this.springPageable = springPageable;
    }
    
    @Override
    public int getPageNumber() {
        return springPageable.getPageNumber();
    }
    
    @Override
    public int getPageSize() {
        return springPageable.getPageSize();
    }
    

    
    @Override
    public Sort getSort() {
        return new SortAdapter(springPageable.getSort());
    }
    
    // Método para converter de volta para Spring Pageable quando necessário
    public org.springframework.data.domain.Pageable toSpringPageable() {
        return springPageable;
    }
    
    // Método estático para converter do domínio para Spring
    public static org.springframework.data.domain.Pageable toSpring(Pageable domainPageable) {
        if (domainPageable instanceof PageableAdapter) {
            return ((PageableAdapter) domainPageable).toSpringPageable();
        }
        
        // Conversão manual se não for um adapter
        return org.springframework.data.domain.PageRequest.of(
            domainPageable.getPageNumber(),
            domainPageable.getPageSize(),
            SortAdapter.toSpring(domainPageable.getSort())
        );
    }
}