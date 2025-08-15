package com.victorxavier.product_catalog.domain.pagination;

import java.util.List;

public interface Sort {
    
    enum Direction {
        ASC, DESC
    }
    
    interface Order {
        String getProperty();
        Direction getDirection();
    }
    
    List<Order> getOrders();
}