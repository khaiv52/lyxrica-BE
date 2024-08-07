package com.vanlang.lyxircaspb.mapper;

import com.vanlang.lyxircaspb.DTO.CategoryDTO;
import com.vanlang.lyxircaspb.DTO.CategoryItemDTO;
import com.vanlang.lyxircaspb.DTO.ProductDTO;
import com.vanlang.lyxircaspb.DTO.SectionDTO;
import com.vanlang.lyxircaspb.model.CategoryItem;
import com.vanlang.lyxircaspb.model.Product;
import com.vanlang.lyxircaspb.model.Section;

import java.util.HashSet;
import java.util.Set;

public class ProductMapper {
    public static ProductDTO toDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setTitle(product.getTitle());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setDiscountedPrice(product.getDiscountedPrice());
        dto.setDiscountPercent(product.getDiscountPercent());
        dto.setQuantity(product.getQuantity());
        dto.setBrand(product.getBrand());
        dto.setColor(product.getColor());
        dto.setSizes(product.getSizes());
        dto.setImageUrl(product.getImageUrl());
        dto.setNumRatings(product.getNumRatings());
        dto.setCreateAt(product.getCreateAt());

        if (product.getCategory() != null) {
            CategoryItem categoryItem = product.getCategory();
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(categoryItem.getId());
            categoryDTO.setName(categoryItem.getName());

            // Retrieve and set Sections associated with this CategoryItem
            Set<SectionDTO> sectionDTOs = new HashSet<>();
            Section section = categoryItem.getParentSection();
            if (section != null) {
                SectionDTO sectionDTO = new SectionDTO();
                sectionDTO.setId(section.getId());
                sectionDTO.setName(section.getName());

                // Handle Category Items
                Set<CategoryItemDTO> categoryItemDTOs = new HashSet<>();
                for (CategoryItem childItem : section.getCategoryItems()) {
                    CategoryItemDTO categoryItemDTO = new CategoryItemDTO();
                    categoryItemDTO.setId(childItem.getId());
                    categoryItemDTO.setName(childItem.getName());
                    categoryItemDTOs.add(categoryItemDTO);
                }
                sectionDTO.setCategoryItems(categoryItemDTOs);
                sectionDTOs.add(sectionDTO);
            }
            categoryDTO.setSections(sectionDTOs);
            dto.setCategory(categoryDTO);
        }
        return dto;
    }
}
