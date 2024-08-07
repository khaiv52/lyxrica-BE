package com.vanlang.lyxircaspb.DTO;

import java.util.Set;

public class SectionDTO {
    private Long id;
    private String name;
    private int level;
    private Set<CategoryItemDTO> categoryItems;

    public SectionDTO() {}

    public SectionDTO(Long id, String name, int level, Set<CategoryItemDTO> categoryItems) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.categoryItems = categoryItems;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CategoryItemDTO> getCategoryItems() {
        return categoryItems;
    }

    public void setCategoryItems(Set<CategoryItemDTO> categoryItems) {
        this.categoryItems = categoryItems;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}

