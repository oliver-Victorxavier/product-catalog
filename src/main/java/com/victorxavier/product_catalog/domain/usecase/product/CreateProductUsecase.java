package com.victorxavier.product_catalog.domain.usecase.product;

import com.victorxavier.product_catalog.domain.entity.Product;

public interface CreateProductUsecase {
    Product create(Product product);
}
