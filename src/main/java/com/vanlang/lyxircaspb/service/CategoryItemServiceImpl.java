package com.vanlang.lyxircaspb.service;

import com.vanlang.lyxircaspb.model.CategoryItem;
import com.vanlang.lyxircaspb.repository.CategoryItemRepository;
import com.vanlang.lyxircaspb.repository.CategoryRepository;
import com.vanlang.lyxircaspb.repository.SectionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryItemServiceImpl implements CategoryItemService {
    private final CategoryItemRepository categoryItemRepository;
    private final CategoryRepository categoryRepository;

    public CategoryItemServiceImpl(CategoryItemRepository categoryItemRepository, CategoryRepository categoryRepository, SectionRepository sectionRepository) {
        this.categoryItemRepository = categoryItemRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void deleteCategory(Long categoryItemId) {
        Optional<CategoryItem> categoryItemOpt = categoryItemRepository.findById(categoryItemId);
        if (categoryItemOpt.isEmpty()) {
            throw new EntityNotFoundException("Category with Item ID: " + categoryItemId + " not found");
        }

        // Xóa Category Item
        categoryItemRepository.deleteById(categoryItemId);
    }

    @Override
    public List<CategoryItem> findAll() {
        return categoryItemRepository.findAll();
    }

    @Override
    public CategoryItem updatedCategoryItem(Long categoryItemId, CategoryItem updatedCategoryItem) {
        if (categoryItemRepository.existsById(categoryItemId)) {
            updatedCategoryItem.setId(categoryItemId);
            return categoryItemRepository.save(updatedCategoryItem);
        } else {
            throw new EntityNotFoundException("Category with Item ID: " + categoryItemId + " not found");
        }
    }
}
