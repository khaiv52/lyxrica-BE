package com.vanlang.lyxircaspb.service;

import com.vanlang.lyxircaspb.model.Category;
import com.vanlang.lyxircaspb.request.CreateCategoryRequest;
import com.vanlang.lyxircaspb.request.UpdateSecondLevelRequest;
import com.vanlang.lyxircaspb.request.UpdateThirdLevelRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {
    Page<Category> getAllPageCategories(String searchText, int pageNumber, int pageSize);
    List<Category> getAllCategories();
    Category createCategories(CreateCategoryRequest req);
    void deleteCategory(Long categoryId);
    void updateTopLevelCategory(Long id, String newName);
    void updateSecondLevelCategory(Long id, UpdateSecondLevelRequest req);
    void updateThirdLevelCategory(Long id, UpdateThirdLevelRequest req) throws Exception;
    Category getTopLevelCategoryById(Long id);
}
