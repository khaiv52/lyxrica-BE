package com.vanlang.lyxircaspb.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "section")
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JsonBackReference
    private Category parentCategory;

    private int level;

    @OneToMany(mappedBy = "parentSection")
    @JsonManagedReference
    private Set<CategoryItem> categoryItems;

    public Section() {
    }

    public Section(Long id, String name, Category parentCategory, Set<CategoryItem> categoryItems, int level) {
        this.id = id;
        this.name = name;
        this.parentCategory = parentCategory;
        this.categoryItems = categoryItems;
        this.level = level;
    }

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

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public Set<CategoryItem> getCategoryItems() {
        return categoryItems;
    }

    public void setCategoryItems(Set<CategoryItem> categoryItems) {
        this.categoryItems = categoryItems;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
