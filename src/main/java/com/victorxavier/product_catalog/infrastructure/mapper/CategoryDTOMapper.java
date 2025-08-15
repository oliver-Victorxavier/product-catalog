package com.victorxavier.product_catalog.infrastructure.mapper;

import com.victorxavier.product_catalog.domain.dto.CategoryDTO;
import com.victorxavier.product_catalog.domain.entity.Category;
import com.victorxavier.product_catalog.domain.mapper.CategoryDomainMapper;

public class CategoryDTOMapper implements CategoryDomainMapper {

    @Override
    public CategoryDTO toDTO(Category category) {
        if (category == null) {
            return null;
        }
        return new CategoryDTO(category.getId(), category.getName());
    }

    @Override
    public Category toDomain(CategoryDTO dto) {
        if (dto == null) {
            return null;
        }
        Category category = new Category();
        category.setId(dto.id());
        category.setName(dto.name());
        return category;
    }
}