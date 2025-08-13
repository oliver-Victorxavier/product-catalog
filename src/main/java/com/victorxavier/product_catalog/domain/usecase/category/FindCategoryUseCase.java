package com.victorxavier.product_catalog.domain.usecase.category;

import com.victorxavier.product_catalog.domain.dto.CategoryDTO;

import java.util.List;

public interface FindCategoryUseCase {
    List<CategoryDTO> findAll();
    List<CategoryDTO> findAllPaged(int page, int size);
    CategoryDTO findById(Long id);
}
