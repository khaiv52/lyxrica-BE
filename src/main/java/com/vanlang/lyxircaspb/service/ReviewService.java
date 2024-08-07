package com.vanlang.lyxircaspb.service;

import com.vanlang.lyxircaspb.exception.ProductException;
import com.vanlang.lyxircaspb.model.Review;
import com.vanlang.lyxircaspb.model.User;
import com.vanlang.lyxircaspb.request.ReviewRequest;

import java.util.List;

public interface ReviewService {
    public Review createReview(ReviewRequest req, User user) throws ProductException;
    public List<Review> getAllReviews(Long productId);
}
