package com.vanlang.lyxrica_springb.service;

import com.vanlang.lyxrica_springb.exception.ProductException;
import com.vanlang.lyxrica_springb.model.Product;
import com.vanlang.lyxrica_springb.model.Review;
import com.vanlang.lyxrica_springb.model.User;
import com.vanlang.lyxrica_springb.repository.ProductRepository;
import com.vanlang.lyxrica_springb.repository.ReviewRepository;
import com.vanlang.lyxrica_springb.request.ReviewRequest;
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
