package com.victorxavier.product_catalog.domain.usecase.category;

import com.victorxavier.product_catalog.domain.dto.CategoryDTO;
import com.victorxavier.product_catalog.domain.pagination.Page;

import java.util.List;

public interface FindCategoryUseCase {

    Page<CategoryDTO> findAllPaged(int page, int size);
    CategoryDTO findById(Long id);
}
