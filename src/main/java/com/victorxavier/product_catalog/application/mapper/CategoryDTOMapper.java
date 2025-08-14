package com.victorxavier.product_catalog.application.mapper;

import com.victorxavier.product_catalog.domain.dto.CategoryDTO;
import com.victorxavier.product_catalog.domain.entity.Category;

public class CategoryDTOMapper {
    public CategoryDTO toDTO (Category entity){
        return new CategoryDTO(entity.getId(), entity.getName());
    }
    public Category toEntity (CategoryDTO dto){
        Category entity = new Category();
        entity.setName(dto.name());
        return entity;
    }
}
