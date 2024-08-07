package com.vanlang.lyxircaspb.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "featured")
public class Featured {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String href;

    @NotNull
    private String imageSrc;

    @NotNull
    private String imageAlt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private Category category;


    public Featured(Long id, String name, String href, String imageSrc, String imageAlt, Category category) {
        this.id = id;
        this.name = name;
        this.href = href;
        this.imageSrc = imageSrc;
        this.imageAlt = imageAlt;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public @NotNull String getHref() {
        return href;
    }

    public void setHref(@NotNull String href) {
        this.href = href;
    }

    public @NotNull String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(@NotNull String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public @NotNull String getImageAlt() {
        return imageAlt;
    }

    public void setImageAlt(@NotNull String imageAlt) {
        this.imageAlt = imageAlt;
    }

    public Featured() {
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


}