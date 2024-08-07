package com.vanlang.lyxircaspb.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "category")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Category name must not be null")
    @Size(max = 50)
    private String name;

    private int level;

    @OneToMany(mappedBy = "category")
    @JsonIgnoreProperties("category")
    @JsonManagedReference
    private List<Featured> features;

    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Section> sections;

    public Category() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull(message = "Category name must not be null") @Size(max = 50) String getName() {
        return name;
    }

    public void setName(@NotNull(message = "Category name must not be null") @Size(max = 50) String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<Featured> getFeatures() {
        return features;
    }

    public void setFeatures(List<Featured> features) {
        this.features = features;
    }

    public Set<Section> getSections() {
        return sections;
    }

    public void setSections(Set<Section> sections) {
        this.sections = sections;
    }
}
