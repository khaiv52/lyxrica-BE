package com.vanlang.lyxrica_springb.mapper;

import com.vanlang.lyxrica_springb.DTO.CategoryDTO;
import com.vanlang.lyxrica_springb.DTO.ProductDTO;
import com.vanlang.lyxrica_springb.model.Product;

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
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(product.getCategory().getId());
            categoryDTO.setName(product.getCategory().getName());
            categoryDTO.setParentCategoryId(product.getCategory().getParentCategory() != null ?
                    product.getCategory().getParentCategory().getId() : null);
            dto.setCategory(categoryDTO);
        }

        return dto;
    }
}
