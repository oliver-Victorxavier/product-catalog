package com.victorxavier.product_catalog.infrastructure.persistence.jpa;

import com.victorxavier.product_catalog.infrastructure.persistence.entity.ProductEntity;
import com.victorxavier.product_catalog.infrastructure.persistence.projection.ProductProjectionImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {

    @Query(nativeQuery = true, value = """
            SELECT * FROM (
            SELECT DISTINCT tb_product.id, tb_product.name
            FROM tb_product
            INNER JOIN tb_product_category ON tb_product_category.product_id = tb_product.id
            WHERE (:categoryIds IS NULL OR tb_product_category.category_id IN :categoryIds)
            AND (LOWER(tb_product.name) LIKE LOWER(CONCAT('%',:name,'%')))
            ) AS tb_result
            """,
            countQuery = """
            SELECT COUNT(*) FROM (
            SELECT DISTINCT tb_product.id, tb_product.name
            FROM tb_product
            INNER JOIN tb_product_category ON tb_product_category.product_id = tb_product.id
            WHERE (:categoryIds IS NULL OR tb_product_category.category_id IN :categoryIds)
            AND (LOWER(tb_product.name) LIKE LOWER(CONCAT('%',:name,'%')))
            ) AS tb_result
            """)
    Page<ProductProjectionImpl> searchProducts(List<Long> categoryIds, String name, Pageable pageable);

    @Query("SELECT obj FROM ProductEntity obj JOIN FETCH obj.categories WHERE obj.id IN :productIds")
    List<ProductEntity> searchProductsWithCategories(List<Long> productIds);
}