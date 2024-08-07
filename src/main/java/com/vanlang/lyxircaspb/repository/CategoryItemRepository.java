package com.vanlang.lyxircaspb.repository;

import com.vanlang.lyxircaspb.model.CategoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryItemRepository extends JpaRepository<CategoryItem, Long> {
    @Query("SELECT ci FROM CategoryItem ci WHERE ci.name = :name AND ci.parentSection.name = :parentSectionName")
    CategoryItem findByNameAndParentSectionName(@Param("name") String name, @Param("parentSectionName") String parentSectionName);
}
