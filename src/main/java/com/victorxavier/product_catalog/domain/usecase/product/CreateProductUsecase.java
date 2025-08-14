package com.victorxavier.product_catalog.domain.usecase.product;

import com.victorxavier.product_catalog.domain.dto.ProductDTO;

public interface CreateProductUsecase {
    ProductDTO create(ProductDTO productDTO);
}
