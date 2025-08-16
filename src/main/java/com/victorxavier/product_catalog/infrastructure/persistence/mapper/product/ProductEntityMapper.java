package com.victorxavier.product_catalog.infrastructure.persistence.mapper.product;

import com.victorxavier.product_catalog.domain.entity.Category;
import com.victorxavier.product_catalog.domain.entity.Product;
import com.victorxavier.product_catalog.infrastructure.persistence.entity.ProductEntity;
import com.victorxavier.product_catalog.infrastructure.persistence.mapper.category.CategoryEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ProductEntityMapper {

    private final CategoryEntityMapper categoryMapper;

    @Autowired
    public ProductEntityMapper(CategoryEntityMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    public Product toDomain(ProductEntity entity) {
        if (entity == null) {
            return null;
        }

        Product product = new Product(entity.getId(), entity.getName(), entity.getDescription(),
                entity.getPrice(), entity.getImgUrl(), entity.getDate());

        
        if (entity.getCategories() != null) {
            entity.getCategories().forEach(categoryEntity -> {
                Category category = categoryMapper.toDomain(categoryEntity);
                product.addCategory(category);
            });
        }

        return product;
    }

    public ProductEntity toEntity(Product product) {
        if (product == null) {
            return null;
        }

        ProductEntity entity = new ProductEntity();
        entity.setName(product.getName());
        entity.setDescription(product.getDescription());
        entity.setPrice(product.getPrice());
        entity.setImgUrl(product.getImgUrl());
        entity.setDate(product.getDate());

        // Map categories
        if (product.getCategories() != null) {
            entity.setCategories(product.getCategories().stream()
                    .map(categoryMapper::toEntity)
                    .collect(Collectors.toSet()));
        }

        return entity;
    }
}