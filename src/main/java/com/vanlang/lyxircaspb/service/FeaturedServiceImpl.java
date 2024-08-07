package com.vanlang.lyxircaspb.service;

import com.vanlang.lyxircaspb.model.Category;
import com.vanlang.lyxircaspb.model.Featured;
import com.vanlang.lyxircaspb.repository.CategoryRepository;
import com.vanlang.lyxircaspb.repository.FeaturedRepository;
import com.vanlang.lyxircaspb.request.CreateFeaturedRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeaturedServiceImpl implements FeaturedService {
    private final FeaturedRepository featuredRepository;
    private final CategoryRepository categoryRepository;

    public FeaturedServiceImpl(FeaturedRepository featuredRepository, CategoryRepository categoryRepository) {
        this.featuredRepository = featuredRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Featured> getAllFeatured() {
        return featuredRepository.findAll();
    }

    @Override
    public Featured createFeatured(CreateFeaturedRequest request) {

        // Tìm kiếm category dựa trên categoryId
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        Featured featured = new Featured();
        featured.setName(request.getName());
        featured.setHref(request.getHref());
        featured.setImageSrc(request.getImageSrc());
        featured.setImageAlt(request.getImageAlt());
        featured.setCategory(category);

        featuredRepository.save(featured);
        return featured;
    }
}
