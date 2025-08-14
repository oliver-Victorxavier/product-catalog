package com.victorxavier.product_catalog.domain.usecase.product;

import com.victorxavier.product_catalog.domain.dto.ProductDTO;

public interface UpdateProductUseCase {
    ProductDTO update(Long id, ProductDTO productDTO);
}
