package com.vanlang.lyxircaspb.service;

import com.vanlang.lyxircaspb.exception.ProductException;
import com.vanlang.lyxircaspb.model.Product;
import com.vanlang.lyxircaspb.model.Rating;
import com.vanlang.lyxircaspb.model.User;
import com.vanlang.lyxircaspb.repository.RatingRepository;
import com.vanlang.lyxircaspb.request.RatingRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    private RatingRepository ratingRepository;
    private ProductService productService;

    public RatingServiceImpl(ProductService productService, RatingRepository ratingRepository) {
        this.productService = productService;
        this.ratingRepository = ratingRepository;
    }

    @Override
    public Rating createRating(RatingRequest req, User user) throws ProductException {
        Product product = productService.findProductById(req.getProductId());
        Rating rating = new Rating();
        rating.setProduct(product);
        rating.setUser(user);
        rating.setRating(req.getRating());
        rating.setCreatedAt(LocalDateTime.now());
        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getProductRatings(Long productId) throws ProductException {
        return ratingRepository.getAllProductsRating(productId);
    }
}
