package com.victorxavier.product_catalog.infrastructure.persistence.jpa;

import com.victorxavier.product_catalog.infrastructure.persistence.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, Long> {

}