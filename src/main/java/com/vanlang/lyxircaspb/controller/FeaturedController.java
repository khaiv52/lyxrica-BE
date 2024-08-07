package com.vanlang.lyxircaspb.controller;

import com.vanlang.lyxircaspb.model.Featured;
import com.vanlang.lyxircaspb.service.FeaturedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/featured")
public class FeaturedController {
    @Autowired
    private FeaturedService featuredService;

    public FeaturedController(FeaturedService featuredService) {
        this.featuredService = featuredService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Featured>> getAll() {
        List<Featured> features = featuredService.getAllFeatured();
        return new ResponseEntity<>(features, HttpStatus.OK);
    }
}
