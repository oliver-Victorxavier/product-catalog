package com.victorxavier.product_catalog.domain.usecase.product;

import com.victorxavier.product_catalog.domain.dto.ProductDTO;

public interface UpdateProductUseCase {
    ProductDTO updateProduct(Long id, ProductDTO productDTO);
}
