package com.victorxavier.product_catalog.infrastructure.persistence.adapter;

import com.victorxavier.product_catalog.domain.pagination.Sort;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class SortAdapter implements Sort {
    
    private final org.springframework.data.domain.Sort springSort;
    
    public SortAdapter(org.springframework.data.domain.Sort springSort) {
        this.springSort = springSort;
    }
    
    @Override
    public List<Order> getOrders() {
        return springSort.stream()
            .map(this::convertOrder)
            .collect(Collectors.toList());
    }
    

    

    
    private Order convertOrder(org.springframework.data.domain.Sort.Order springOrder) {
        return new OrderAdapter(springOrder);
    }

    public org.springframework.data.domain.Sort toSpringSort() {
        return springSort;
    }

    public static org.springframework.data.domain.Sort toSpring(Sort domainSort) {
        if (domainSort instanceof SortAdapter) {
            return ((SortAdapter) domainSort).springSort;
        }
        List<org.springframework.data.domain.Sort.Order> springOrders = domainSort.getOrders().stream()
            .map(order -> {
                org.springframework.data.domain.Sort.Direction direction = 
                    order.getDirection() == Direction.ASC ? 
                        org.springframework.data.domain.Sort.Direction.ASC : 
                        org.springframework.data.domain.Sort.Direction.DESC;
                return new org.springframework.data.domain.Sort.Order(direction, order.getProperty());
            })
            .collect(Collectors.toList());
        
        return org.springframework.data.domain.Sort.by(springOrders);
    }
    
    private static class OrderAdapter implements Order {
        private final org.springframework.data.domain.Sort.Order springOrder;
        
        public OrderAdapter(org.springframework.data.domain.Sort.Order springOrder) {
            this.springOrder = springOrder;
        }
        
        @Override
        public String getProperty() {
            return springOrder.getProperty();
        }
        
        @Override
        public Direction getDirection() {
            return springOrder.getDirection() == org.springframework.data.domain.Sort.Direction.ASC ? 
                Direction.ASC : Direction.DESC;
        }
        

    }
}