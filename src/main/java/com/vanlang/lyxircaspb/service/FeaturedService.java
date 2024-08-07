package com.vanlang.lyxircaspb.service;

import com.vanlang.lyxircaspb.model.Featured;
import com.vanlang.lyxircaspb.request.CreateFeaturedRequest;

import java.util.List;

public interface FeaturedService {
    public Featured createFeatured(CreateFeaturedRequest request);
    public List<Featured> getAllFeatured();
}
