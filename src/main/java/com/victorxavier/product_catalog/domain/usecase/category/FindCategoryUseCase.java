package com.victorxavier.product_catalog.domain.usecase.category;

import com.victorxavier.product_catalog.domain.dto.CategoryDTO;
import com.victorxavier.product_catalog.domain.pagination.Page;


public interface FindCategoryUseCase {

    Page<CategoryDTO> findAllPaged(int page, int size);
    CategoryDTO findById(Long id);
}
