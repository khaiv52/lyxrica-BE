package com.vanlang.lyxircaspb.controller;

import com.vanlang.lyxircaspb.DTO.CategoryDTO;
import com.vanlang.lyxircaspb.mapper.CategoryMapper;
import com.vanlang.lyxircaspb.model.Category;
import com.vanlang.lyxircaspb.model.Section;
import com.vanlang.lyxircaspb.service.CategoryService;
import com.vanlang.lyxircaspb.service.SectionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final SectionService sectionService;

    @Autowired
    public CategoryController(CategoryService categoryService, SectionService sectionService) {
        this.categoryService = categoryService;
        this.sectionService = sectionService;
    }

    @GetMapping("/all-pages")
    public ResponseEntity<Page<CategoryDTO>> getAllCategoriesPage(
            @RequestParam("searchText") String searchText,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Category> res = categoryService.getAllPageCategories(searchText, pageNumber, pageSize);
        Page<CategoryDTO> categoryDTOS = res.map(CategoryMapper::toDTO);
        return new ResponseEntity<>(categoryDTOS, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok("Deleted Category Successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/top-level")
    public ResponseEntity<Category> getTopLevelCategory(
            @RequestParam("id") Long id) {
        Category category = categoryService.getTopLevelCategoryById(id);
        if (category != null) {
            return ResponseEntity.ok(category);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/second-level")
    public List<Section> getSections(@RequestParam Long categoryId, @RequestParam int level) {
        return sectionService.getSectionsByCategoryIdAndLevel(categoryId, level);
    }
}