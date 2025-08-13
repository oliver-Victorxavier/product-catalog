package com.victorxavier.product_catalog.domain.usecase.product;

import com.victorxavier.product_catalog.domain.dto.ProductDTO;
import com.victorxavier.product_catalog.domain.pagination.Page;
import com.victorxavier.product_catalog.domain.pagination.PageRequest;

public interface FindProductUseCase {

    ProductDTO findById(Long id);
    Page<ProductDTO> findAllPaged(String categoryId, String name, PageRequest pageRequest);

}

