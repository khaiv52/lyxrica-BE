package com.vanlang.lyxircaspb.controller;

import com.vanlang.lyxircaspb.model.CategoryItem;
import com.vanlang.lyxircaspb.service.CategoryItemService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category-items")
public class CategoryItemController {
    @Autowired
    private final CategoryItemService categoryItemService;

    public CategoryItemController(CategoryItemService categoryItemService) {
        this.categoryItemService = categoryItemService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryItem>> getAllSections() {
        List<CategoryItem> categoryItems = categoryItemService.findAll();
        return new ResponseEntity<>(categoryItems, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            categoryItemService.deleteCategory(id);
            return ResponseEntity.ok("CategoryItem Deleted Successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<CategoryItem> updateCategoryItem(@PathVariable Long id, @RequestBody CategoryItem categoryItem) {
        CategoryItem updatedCategoryItem = categoryItemService.updatedCategoryItem(id, categoryItem);
        if (updatedCategoryItem != null) {
            return new ResponseEntity<>(updatedCategoryItem, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
