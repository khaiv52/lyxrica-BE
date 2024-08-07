package com.vanlang.lyxircaspb.controller;

import com.vanlang.lyxircaspb.model.Featured;
import com.vanlang.lyxircaspb.request.CreateFeaturedRequest;
import com.vanlang.lyxircaspb.service.FeaturedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin/featured")
public class AdminFeaturedController {
    @Autowired
    private final FeaturedService featuredService;

    public AdminFeaturedController(FeaturedService featuredService) {
        this.featuredService = featuredService;
    }

    @PostMapping("/create")
    public ResponseEntity<Featured> create(@RequestHeader("Authorization") String jwt, @RequestBody CreateFeaturedRequest req) {
        Featured featured = featuredService.createFeatured(req);
        return new ResponseEntity<>(featured, HttpStatus.CREATED);
    }
}
