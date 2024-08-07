package com.vanlang.lyxircaspb.service;

import com.vanlang.lyxircaspb.model.Category;
import com.vanlang.lyxircaspb.model.CategoryItem;
import com.vanlang.lyxircaspb.model.Section;
import com.vanlang.lyxircaspb.repository.CategoryItemRepository;
import com.vanlang.lyxircaspb.repository.CategoryRepository;
import com.vanlang.lyxircaspb.repository.SectionRepository;
import com.vanlang.lyxircaspb.request.CreateCategoryRequest;
import com.vanlang.lyxircaspb.request.UpdateSecondLevelRequest;
import com.vanlang.lyxircaspb.request.UpdateThirdLevelRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private final CategoryRepository categoryRepository;
    @Autowired
    private final SectionRepository sectionRepository;
    @Autowired
    private final CategoryItemRepository categoryItemRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, SectionRepository sectionRepository, CategoryItemRepository categoryItemRepository) {
        this.categoryRepository = categoryRepository;
        this.sectionRepository = sectionRepository;
        this.categoryItemRepository = categoryItemRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Page<Category> getAllPageCategories(String searchText, int pageNumber, int pageSize) {
        // Tạo đối tượng Pageable để quản lý phân trang
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        // Lấy tất cả các sản phẩm theo tiêu chí lọc
        Page<Category> categories = categoryRepository.findBySearchText(searchText, pageable);
        return categories;
    }

    @Override
    public Category createCategories(CreateCategoryRequest req) {
        // Kiểm tra và tạo danh mục cấp 1 nếu không tồn tại
        Category topLevel = categoryRepository.findByName(req.getTopLevelCategory());

        if (topLevel == null) {
            Category newTopLevel = new Category();
            newTopLevel.setName(req.getTopLevelCategory());
            newTopLevel.setLevel(0);  // Giả sử cấp độ là 0 cho danh mục cấp 1
            topLevel = categoryRepository.save(newTopLevel);
        }

        // Kiểm tra và tạo danh mục cấp 2 nếu không tồn tại
        Section secondLevel = sectionRepository.findByNameAndParentCategoryName(req.getSecondLevelSectionName(), topLevel.getName());

        if (secondLevel == null) {
            Section newSecondLevel = new Section();
            newSecondLevel.setName(req.getSecondLevelSectionName());
            newSecondLevel.setLevel(1);
            newSecondLevel.setParentCategory(topLevel);
            secondLevel = sectionRepository.save(newSecondLevel);
        }

        // Kiểm tra và tạo danh mục cấp 3 nếu không tồn tại
        CategoryItem thirdLevel = categoryItemRepository.findByNameAndParentSectionName(req.getThirdLevelItemName(), secondLevel.getName());

        if (thirdLevel == null) {
            CategoryItem newThirdLevel = new CategoryItem();
            newThirdLevel.setName(req.getThirdLevelItemName());
            newThirdLevel.setLevel(2);
            newThirdLevel.setParentSection(secondLevel);
            thirdLevel = categoryItemRepository.save(newThirdLevel);
        }

        // Trả về danh mục cấp 1
        return topLevel;
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Optional<Category> categoryOpt = categoryRepository.findById(categoryId);

        if (categoryOpt.isEmpty()) {
            throw new EntityNotFoundException("Category with ID " + categoryId + " not found");
        }

        Category category = categoryOpt.get();
        // Xóa tất cả các categoryItems thuộc Section của Category
        category.getSections().forEach(section -> {
            section.getCategoryItems().forEach(categoryItem -> categoryItemRepository.deleteById(categoryItem.getId()));
        });

        // Xóa tất cả Sections thuộc Category
        category.getSections().forEach(section -> sectionRepository.deleteById(section.getId()));

        // Xóa tất cả category
        categoryRepository.deleteById(categoryId);
    }

    @Override
    public void updateTopLevelCategory(Long id, String newName) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        category.setName(newName);
        categoryRepository.save(category);
    }

    @Override
    public void updateSecondLevelCategory(Long id, UpdateSecondLevelRequest req) {
        Section section = sectionRepository.findById(id).orElseThrow(() -> new RuntimeException("Section not found"));

        Category parentCategory = categoryRepository.findById(req.getParentCategoryId()).orElseThrow(() -> new RuntimeException("Parent category not found"));

        section.setName(req.getNewName());
        section.setParentCategory(parentCategory);
        sectionRepository.save(section);
    }

    @Override
    public void updateThirdLevelCategory(Long id, UpdateThirdLevelRequest req) {
        // In ra ID của danh mục cấp 3 để kiểm tra
        System.out.println("Updating CategoryItem with ID: " + id);

        // Tìm danh mục cấp 3 dựa trên ID
        CategoryItem categoryItem = categoryItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("CategoryItem not found"));

        // In ra thông tin danh mục cấp 3 để kiểm tra
        System.out.println("Found CategoryItem: " + categoryItem.getName());

        // In ra ID của danh mục cấp 2 từ yêu cầu để kiểm tra
        System.out.println("Parent Section ID from request: " + req.getParentSectionId());

        // Kiểm tra danh mục cấp 2
        Section section = sectionRepository.findById(req.getParentSectionId())
                .orElseThrow(() -> new EntityNotFoundException("Section not found"));

        // In ra thông tin danh mục cấp 2 để kiểm tra
        System.out.println("Found Section: " + section.getName());

        // Cập nhật thông tin
        categoryItem.setName(req.getNewName());
        categoryItem.setParentSection(section);

        // Lưu cập nhật vào cơ sở dữ liệu
        categoryItemRepository.save(categoryItem);

        // In ra thông báo thành công để kiểm tra
        System.out.println("CategoryItem updated successfully");
    }

    @Override
    public Category getTopLevelCategoryById(Long id) {
        return categoryRepository.findTopLevelCategoryById(id);
    }
}
