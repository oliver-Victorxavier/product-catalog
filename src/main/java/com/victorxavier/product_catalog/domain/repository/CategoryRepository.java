package com.victorxavier.product_catalog.domain.repository;

import com.victorxavier.product_catalog.domain.dto.CategoryDTO;
import com.victorxavier.product_catalog.domain.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    Category save(Category category);
    Optional<Category> findById(Long id);
    List<Category> findAll();
    Page<Category> findAllPaged(Pageable pageable);
    void deleteById(Long id);
}
