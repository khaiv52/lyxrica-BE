package com.vanlang.lyxircaspb.DTO;

import java.util.Set;

public class CategoryDTO {
    private Long id;
    private String name;
    private int level;
    private Set<SectionDTO> sections;

    public CategoryDTO() {}

    public CategoryDTO(Long id, String name, int level, Set<SectionDTO> sections) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.sections = sections;
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

    public Set<SectionDTO> getSections() {
        return sections;
    }

    public void setSections(Set<SectionDTO> sections) {
        this.sections = sections;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
