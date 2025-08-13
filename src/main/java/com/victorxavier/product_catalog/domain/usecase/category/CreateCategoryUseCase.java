package com.victorxavier.product_catalog.domain.usecase.category;

import com.victorxavier.product_catalog.domain.dto.CategoryDTO;

public interface CreateCategoryUseCase {

    CategoryDTO create(CategoryDTO categoryDTO);
}
