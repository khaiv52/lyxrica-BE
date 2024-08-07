package com.vanlang.lyxircaspb.mapper;

import com.vanlang.lyxircaspb.DTO.CategoryDTO;
import com.vanlang.lyxircaspb.DTO.CategoryItemDTO;
import com.vanlang.lyxircaspb.DTO.SectionDTO;
import com.vanlang.lyxircaspb.model.Category;

import java.util.Set;
import java.util.stream.Collectors;

public class CategoryMapper {
    public static CategoryDTO toDTO(Category category) {
        if (category == null) {
            return null;
        }

        Set<SectionDTO> sectionDTOs = category.getSections().stream()
                .map(section -> new SectionDTO(
                        section.getId(),
                        section.getName(),
                        section.getLevel(),
                        section.getCategoryItems().stream()
                                .map(item -> new CategoryItemDTO(item.getId(), item.getName(), item.getLevel()))
                                .collect(Collectors.toSet())
                ))
                .collect(Collectors.toSet());

        return new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getLevel(),
                sectionDTOs
        );
    }

    public static Category toEntity(CategoryDTO categoryDTO) {
        if (categoryDTO == null) {
            return null;
        }
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());
        category.setLevel(categoryDTO.getLevel());
        // Handle other properties if needed
        return category;
    }
}
