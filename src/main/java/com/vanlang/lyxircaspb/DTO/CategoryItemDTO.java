package com.vanlang.lyxircaspb.DTO;

public class CategoryItemDTO {
    private Long id;
    private String name;
    private int level;

    public CategoryItemDTO() {}

    public CategoryItemDTO(Long id, String name, int level) {
        this.id = id;
        this.name = name;
        this.level = level;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
