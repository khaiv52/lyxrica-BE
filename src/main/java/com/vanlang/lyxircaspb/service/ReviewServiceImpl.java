package com.vanlang.lyxircaspb.service;

import com.vanlang.lyxircaspb.exception.ProductException;
import com.vanlang.lyxircaspb.model.Product;
import com.vanlang.lyxircaspb.model.Review;
import com.vanlang.lyxircaspb.model.User;
import com.vanlang.lyxircaspb.repository.ProductRepository;
import com.vanlang.lyxircaspb.repository.ReviewRepository;
import com.vanlang.lyxircaspb.request.ReviewRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductService productService;
    private final ProductRepository productRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ProductService productService, ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @Override
    public Review createReview(ReviewRequest req, User user) throws ProductException {
        Product product = productService.findProductById(req.getProductId());
        Review review = new Review();
        review.setProduct(product);
        review.setUser(user);
        review.setReview(req.getReview());
        review.setCreatedAt(LocalDateTime.now());
        System.out.println(req.getProductId());

        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getAllReviews(Long productId) {
        return reviewRepository.getAllProductReviews(productId);
    }
}
