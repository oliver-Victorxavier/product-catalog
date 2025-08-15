package com.victorxavier.product_catalog.domain.projection;

import java.math.BigDecimal;
import java.time.Instant;

public class ProductProjection {

    private Long id;
    private String name;
    private BigDecimal price;
    private Instant date;
    private String description;
    private String imgUrl;

    public ProductProjection(Long id, String name, BigDecimal price, Instant date, String description, String imgUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.date = date;
        this.description = description;
        this.imgUrl = imgUrl;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Instant getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
