package com.vanlang.lyxircaspb.repository;

import com.vanlang.lyxircaspb.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p " +
            "LEFT JOIN OrderItem oi ON oi.product.id = p.id " +
            "LEFT JOIN oi.order o " +
            "WHERE (:category IS NULL OR p.category.name = :category) " +
            "AND (:parentCategoryName IS NULL OR p.category.parentSection.name = :parentCategoryName) " +
            "AND (:minPrice IS NULL OR p.discountedPrice >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.discountedPrice <= :maxPrice) " +
            "AND (:minDiscount IS NULL OR p.discountPercent >= :minDiscount) " +
            "GROUP BY p.id " +
            "ORDER BY " +
            "CASE WHEN :sort = 'price_low' THEN p.discountedPrice END ASC, " +
            "CASE WHEN :sort = 'price_high' THEN p.discountedPrice END DESC, " +
            "CASE WHEN :sort = 'newest' THEN p.createAt END DESC, " +
            "CASE WHEN :sort = 'best_selling' THEN SUM(oi.quantity) END DESC"
    )
    List<Product> filterProducts(
            @Param("category") String category,
            @Param("parentCategoryName") String parentCategoryName,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("minDiscount") Double minDiscount,
            @Param("sort") String sort
    );

    // Tìm sản phẩm theo tên danh mục
    @Query("SELECT p FROM Product p WHERE p.category.name = :categoryName")
    List<Product> findProductsByCategoryName(@Param("categoryName") String categoryName);

    // Tìm sản phẩm mới nhất
    @Query("SELECT p FROM Product p ORDER BY p.createAt DESC")
    List<Product> findLatestProducts();

    // Tìm kiếm sản phẩm
    @Query("SELECT p FROM Product p " +
            "LEFT JOIN p.category c " +
            "WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
            "OR LOWER(c.name) LIKE LOWER(CONCAT('%', :searchText, '%'))")
    Page<Product> findProductBySearchText(@Param("searchText") String searchText, Pageable pageable);
}

