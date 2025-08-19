package com.victorxavier.product_catalog.infrastructure.persistence.impl.category;

import com.victorxavier.product_catalog.domain.entity.Category;
import com.victorxavier.product_catalog.domain.repository.CategoryRepository;
import com.victorxavier.product_catalog.infrastructure.persistence.entity.CategoryEntity;
import com.victorxavier.product_catalog.infrastructure.persistence.jpa.CategoryJpaRepository;
import com.victorxavier.product_catalog.infrastructure.persistence.mapper.category.CategoryEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.victorxavier.product_catalog.domain.pagination.Page;
import com.victorxavier.product_catalog.domain.pagination.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
        // Converter Pageable do domínio para Spring Data
        org.springframework.data.domain.Pageable springPageable = convertToSpringPageable(pageable);
        org.springframework.data.domain.Page<CategoryEntity> entityPage =
                categoryJpaRepository.findAll(springPageable);
        List<Category> categories = entityPage.getContent().stream()
            .map(categoryMapper::toDomain)
            .collect(java.util.stream.Collectors.toList());
        return new Page<>(categories, entityPage.getNumber(), entityPage.getSize(), entityPage.getTotalElements());
    }

    @Override
    public void deleteById(Long id) {
        categoryJpaRepository.deleteById(id);
    }
    
    /**
     * Converte Pageable do domínio para Pageable do Spring Data
     */
    private org.springframework.data.domain.Pageable convertToSpringPageable(Pageable domainPageable) {
        if (domainPageable == null) {
            return PageRequest.of(0, 20);
        }

        if (domainPageable.getSort() != null && !domainPageable.getSort().isEmpty()) {
            Sort.Direction direction = domainPageable.getSortDirection() == com.victorxavier.product_catalog.domain.pagination.Sort.Direction.DESC
                ? Sort.Direction.DESC : Sort.Direction.ASC;
            Sort sort = Sort.by(direction, domainPageable.getSort());
            return PageRequest.of(domainPageable.getPageNumber(), domainPageable.getPageSize(), sort);
        }
        return PageRequest.of(domainPageable.getPageNumber(), domainPageable.getPageSize());
    }
}