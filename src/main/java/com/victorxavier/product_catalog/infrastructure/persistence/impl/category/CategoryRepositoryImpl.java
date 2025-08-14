package com.victorxavier.product_catalog.infrastructure.persistence.impl.category;

import com.victorxavier.product_catalog.domain.entity.Category;
import com.victorxavier.product_catalog.domain.repository.CategoryRepository;
import com.victorxavier.product_catalog.infrastructure.persistence.entity.CategoryEntity;
import com.victorxavier.product_catalog.infrastructure.persistence.jpa.CategoryJpaRepository;
import com.victorxavier.product_catalog.infrastructure.persistence.mapper.category.CategoryEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryJpaRepository categoryJpaRepository;
    private final CategoryEntityMapper categoryMapper;

    @Autowired
    public CategoryRepositoryImpl(
            CategoryJpaRepository categoryJpaRepository,
            CategoryEntityMapper categoryMapper) {
        this.categoryJpaRepository = categoryJpaRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public Category save(Category category) {
        var entity = categoryMapper.toEntity(category);
        var savedEntity = categoryJpaRepository.save(entity);
        return categoryMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryJpaRepository.findById(id)
                .map(categoryMapper::toDomain);
    }

    @Override
    public List<Category> findAll() {
        return categoryJpaRepository.findAll().stream()
                .map(categoryMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Page<Category> findAllPaged(Pageable pageable) {
        Page<CategoryEntity> entityPage =
                categoryJpaRepository.findAll(pageable);
        List<Category> categories = entityPage.getContent().stream()
                .map(categoryMapper::toDomain)
                .collect(Collectors.toList());
        return new PageImpl<>(categories, pageable, entityPage.getTotalElements());
    }

    @Override
    public void deleteById(Long id) {
        categoryJpaRepository.deleteById(id);
    }
}