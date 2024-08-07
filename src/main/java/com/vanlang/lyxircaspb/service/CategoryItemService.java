package com.vanlang.lyxircaspb.service;

import com.vanlang.lyxircaspb.model.CategoryItem;

import java.util.List;

public interface CategoryItemService {
    List<CategoryItem> findAll();
    CategoryItem updatedCategoryItem(Long categoryItemId, CategoryItem updatedCategoryItem);
    public void deleteCategory(Long categoryItemId);

}
