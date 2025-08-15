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
            SELECT DISTINCT tb_product.id AS id, tb_product.name AS name, tb_product.price AS price, 
                   tb_product.date AS date, tb_product.description AS description, tb_product.img_url AS imgUrl
            FROM tb_product
            LEFT JOIN tb_product_category ON tb_product_category.product_id = tb_product.id
            WHERE (:categoryIds IS NULL OR tb_product_category.category_id IN (:categoryIds) OR tb_product_category.category_id IS NULL)
            AND (LOWER(tb_product.name) LIKE LOWER(CONCAT('%',:name,'%')))
            """,
            countQuery = """
            SELECT COUNT(*) FROM (
            SELECT DISTINCT tb_product.id
            FROM tb_product
            LEFT JOIN tb_product_category ON tb_product_category.product_id = tb_product.id
            WHERE (:categoryIds IS NULL OR tb_product_category.category_id IN (:categoryIds) OR tb_product_category.category_id IS NULL)
            AND (LOWER(tb_product.name) LIKE LOWER(CONCAT('%',:name,'%')))
            ) AS tb_result
            """)
    Page<ProductProjectionImpl> searchProducts(List<Long> categoryIds, String name, Pageable pageable);

    @Query("SELECT obj FROM ProductEntity obj JOIN FETCH obj.categories WHERE obj.id IN :productIds")
    List<ProductEntity> searchProductsWithCategories(List<Long> productIds);
}