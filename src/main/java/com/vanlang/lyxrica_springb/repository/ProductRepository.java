package com.vanlang.lyxrica_springb.repository;

import com.vanlang.lyxrica_springb.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p " +
            "JOIN p.category c " +
            "LEFT JOIN c.parentCategory pc " +
            "WHERE (:category IS NULL OR c.name = :category) " +
            "AND (:parentCategoryName IS NULL OR pc.name = :parentCategoryName) " +
            "AND (:minPrice IS NULL OR p.discountedPrice >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.discountedPrice <= :maxPrice) " +
            "AND (:minDiscount IS NULL OR p.discountPercent >= :minDiscount) " +
            "ORDER BY " +
            "CASE WHEN :sort = 'price_low' THEN p.discountedPrice END ASC, " +
            "CASE WHEN :sort = 'price_high' THEN p.discountedPrice END DESC"
    )
    List<Product> filterProducts(
            @Param("category") String category,
            @Param("parentCategoryName") String parentCategoryName,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("minDiscount") Double minDiscount,
            @Param("sort") String sort
    );
}

