package com.victorxavier.product_catalog.domain.pagination;

import java.util.List;

public interface Sort {
    List<Order> getOrders();
    
    interface Order {
        String getProperty();
        Direction getDirection();
    }
    
    enum Direction {
        ASC, DESC
    }
}