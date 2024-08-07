package com.vanlang.lyxircaspb.repository;

import com.vanlang.lyxircaspb.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

//    @Query("SELECT c FROM Category c WHERE c.name = :name AND c.parentCategory.name = :parentCategoryName")
//    public Category findByNameAndParent(@Param("name") String name, @Param("parentCategoryName") String parentCategoryName);

    @Query("SELECT c FROM Category c WHERE c.name = :name")
    public Category findByName(@Param("name") String name);

    @Query("SELECT c FROM Category c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :searchText, '%'))")
    public Page<Category> findBySearchText(@Param("searchText") String searchText, Pageable pageable);

    // Tìm cấp 1
    @Query("SELECT c FROM Category c WHERE c.id = :id AND c.level = 0")
    public Category findTopLevelCategoryById(@Param("id") Long id);
}

