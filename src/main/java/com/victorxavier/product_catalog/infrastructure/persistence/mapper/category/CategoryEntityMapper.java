package com.victorxavier.product_catalog.infrastructure.persistence.mapper.category;

import com.victorxavier.product_catalog.domain.entity.Category;
import com.victorxavier.product_catalog.infrastructure.persistence.entity.CategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoryEntityMapper {

    public Category toDomain(CategoryEntity entity) {
        if (entity == null) {
            return null;
        }

        Category category = new Category(entity.getId(), entity.getName());

        return category;
    }

    public CategoryEntity toEntity(Category category) {
        if (category == null) {
            return null;
        }

        CategoryEntity entity = new CategoryEntity();
        entity.setId(category.getId());
        entity.setName(category.getName());

        return entity;
    }
}