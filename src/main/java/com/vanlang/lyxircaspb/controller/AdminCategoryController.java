package com.vanlang.lyxircaspb.controller;

import com.vanlang.lyxircaspb.model.Category;
import com.vanlang.lyxircaspb.request.CreateCategoryRequest;
import com.vanlang.lyxircaspb.request.UpdateSecondLevelRequest;
import com.vanlang.lyxircaspb.request.UpdateThirdLevelRequest;
import com.vanlang.lyxircaspb.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/categories")
public class AdminCategoryController {
    private final CategoryService categoryService;

    @Autowired
    public AdminCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @PostMapping("/create")
    public ResponseEntity<Category> createCategory(@RequestBody (required = false) CreateCategoryRequest req) {
        Category category = categoryService.createCategories(req);

        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @PutMapping("/update/top-level/{id}")
    public ResponseEntity<String> updateTopLevelCategory(@PathVariable Long id, @RequestBody String newName) {
        try {
            categoryService.updateTopLevelCategory(id, newName);
            return ResponseEntity.ok("Top-level category updated successfully");
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error updating top-level category: " + e.getMessage());
        }
    }

    @PutMapping("/update/second-level/{id}")
    public ResponseEntity<String> updateSecondCategory(@PathVariable Long id, @RequestBody UpdateSecondLevelRequest req) {
        try {
            categoryService.updateSecondLevelCategory(id, req);
            return ResponseEntity.ok("Second-level category updated successfully");
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error updating second-level category: " + e.getMessage());
        }
    }

    @PutMapping("/update/third-level/{id}")
    public ResponseEntity<String> updateThirdCategory(@PathVariable Long id, @RequestBody UpdateThirdLevelRequest req) {
        try {
            categoryService.updateThirdLevelCategory(id, req);
            return ResponseEntity.ok("Third-level category updated successfully");
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error updating third-level category: " + e.getMessage());
        }
    }
}

