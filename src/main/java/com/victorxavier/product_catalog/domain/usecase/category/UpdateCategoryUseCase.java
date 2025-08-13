package com.victorxavier.product_catalog.domain.usecase.category;

import com.victorxavier.product_catalog.domain.dto.CategoryDTO;

public interface UpdateCategoryUseCase {
    CategoryDTO update(Long id, CategoryDTO categoryDTO);
}
