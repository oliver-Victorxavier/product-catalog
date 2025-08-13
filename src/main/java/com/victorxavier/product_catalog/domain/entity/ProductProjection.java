package com.victorxavier.product_catalog.domain.entity;

public class ProductProjection {

    private String name;
    private Long id;

    public ProductProjection(String name, Long id) {
        this.name = name;
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public Long getId() {
        return id;
    }
}
