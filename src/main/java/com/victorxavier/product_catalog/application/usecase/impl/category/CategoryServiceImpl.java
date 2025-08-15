package com.victorxavier.product_catalog.application.usecase.impl.category;

import com.victorxavier.product_catalog.domain.dto.CategoryDTO;
import com.victorxavier.product_catalog.domain.entity.Category;
import com.victorxavier.product_catalog.domain.exception.DatabaseException;
import com.victorxavier.product_catalog.domain.exception.ResourceNotFoundException;
import com.victorxavier.product_catalog.domain.pagination.Page;
import com.victorxavier.product_catalog.domain.pagination.Pageable;
import com.victorxavier.product_catalog.domain.repository.CategoryRepository;
import com.victorxavier.product_catalog.domain.usecase.category.CreateCategoryUseCase;
import com.victorxavier.product_catalog.domain.usecase.category.DeleteCategoryUseCase;
import com.victorxavier.product_catalog.domain.usecase.category.FindCategoryUseCase;
import com.victorxavier.product_catalog.domain.usecase.category.UpdateCategoryUseCase;
import com.victorxavier.product_catalog.domain.mapper.CategoryDomainMapper;
import java.util.Optional;

public class CategoryServiceImpl implements CreateCategoryUseCase, FindCategoryUseCase, UpdateCategoryUseCase, DeleteCategoryUseCase {

    private final CategoryRepository repository;
    private final CategoryDomainMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository repository, CategoryDomainMapper categoryMapper){
        this.repository = repository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryDTO create(CategoryDTO categoryDTO) {
        Category entity = new Category();
        entity.setName(categoryDTO.name());
        entity = repository.save(entity);
        return categoryMapper.toDTO(entity);
    }
    @Override
    public CategoryDTO update(Long id, CategoryDTO categoryDTO) {
        try {
            Optional<Category> optionalEntity = repository.findById(id);
            Category entity = optionalEntity.orElseThrow(() -> new ResourceNotFoundException("Id not found" + id));
            entity.setName(categoryDTO.name());
            entity = repository.save(entity);
            return categoryMapper.toDTO(entity);
        }
        catch (Exception e){
            throw new ResourceNotFoundException("Id not found" + id);
        }
    }
    @Override
    public void delete(Long id) {
        if (repository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("resource not found");
        }
        try {
            repository.deleteById(id);
        }
        catch (Exception e){
            throw new DatabaseException("Referential integrity failure");
        }

    }
    @Override
    public CategoryDTO findById(Long id) {
        Optional<Category> obj = repository.findById(id);
        Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return categoryMapper.toDTO(entity);
    }

    @Override
    public Page<CategoryDTO> findAllPaged(int page, int size) {
        Pageable pageable = new Pageable(page, size, null, null);
        Page<Category> categoryPage = repository.findAllPaged(pageable);
        return categoryPage.map(categoryMapper::toDTO);
    }
}
