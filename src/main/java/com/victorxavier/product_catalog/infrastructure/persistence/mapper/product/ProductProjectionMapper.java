package com.victorxavier.product_catalog.infrastructure.persistence.mapper.product;

import com.victorxavier.product_catalog.domain.projection.ProductProjection;
import com.victorxavier.product_catalog.infrastructure.persistence.projection.ProductProjectionImpl;
import org.springframework.stereotype.Component;

@Component
public class ProductProjectionMapper {

    public ProductProjection toDomain(ProductProjectionImpl projection) {
        if (projection == null) {
            return null;
        }

        return new ProductProjection(
                projection.getId(),
                projection.getName(),
                projection.getPrice(),
                projection.getDate(),
                projection.getDescription(),
                projection.getImgUrl()
        );
    }
}