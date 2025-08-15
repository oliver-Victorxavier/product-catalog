package com.victorxavier.product_catalog.domain.mapper;

import com.victorxavier.product_catalog.domain.dto.CategoryDTO;
import com.victorxavier.product_catalog.domain.entity.Category;

public interface CategoryDomainMapper {
    CategoryDTO toDTO(Category category);
    Category toDomain(CategoryDTO categoryDTO);
}