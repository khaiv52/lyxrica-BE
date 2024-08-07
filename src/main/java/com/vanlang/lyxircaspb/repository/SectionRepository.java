package com.vanlang.lyxircaspb.repository;

import com.vanlang.lyxircaspb.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
    @Query("SELECT s FROM Section s WHERE s.name = :name AND s.parentCategory.name = :parentCategoryName")
    Section findByNameAndParentCategoryName(@Param("name") String name, @Param("parentCategoryName") String parentCategoryName);

    // Tìm cấp 2
    @Query("SELECT s FROM Section s WHERE s.parentCategory.id = :categoryId AND s.level = :level")
    List<Section> findSectionsByParentCategoryIdAndLevel(@Param("categoryId") Long categoryId, @Param("level") int level);
}
