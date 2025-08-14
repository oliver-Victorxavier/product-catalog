package com.victorxavier.product_catalog.application.usecase.impl.category;

import com.victorxavier.product_catalog.application.mapper.CategoryDTOMapper;
import com.victorxavier.product_catalog.domain.dto.CategoryDTO;
import com.victorxavier.product_catalog.domain.entity.Category;
import com.victorxavier.product_catalog.domain.exception.DatabaseException;
import com.victorxavier.product_catalog.domain.exception.ResourceNotFoundException;
import com.victorxavier.product_catalog.domain.pagination.Page;
import com.victorxavier.product_catalog.domain.repository.CategoryRepository;
import com.victorxavier.product_catalog.domain.usecase.category.CreateCategoryUseCase;
import com.victorxavier.product_catalog.domain.usecase.category.DeleteCategoryUseCase;
import com.victorxavier.product_catalog.domain.usecase.category.FindCategoryUseCase;
import com.victorxavier.product_catalog.domain.usecase.category.UpdateCategoryUseCase;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CategoryServiceImpl implements CreateCategoryUseCase, FindCategoryUseCase, UpdateCategoryUseCase, DeleteCategoryUseCase {

    private final CategoryRepository repository;
    private final CategoryDTOMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository repository, CategoryDTOMapper categoryMapper){
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
        List<Category> categories = repository.findAll();
        List<CategoryDTO> categoryDTOs = categories.stream()
                .map(categoryMapper::toDTO)
                .collect(Collectors.toList());

        // pagination logic
        int start = page * size;
        int end = Math.min(start + size, categoryDTOs.size());
        List<CategoryDTO> pageContent = start < categoryDTOs.size() ?
                categoryDTOs.subList(start, end) : List.of();

        return new Page<>(pageContent, page, size, categoryDTOs.size());
    }
}
