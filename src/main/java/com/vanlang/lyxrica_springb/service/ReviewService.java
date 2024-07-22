package com.vanlang.lyxrica_springb.service;

import com.vanlang.lyxrica_springb.exception.ProductException;
import com.vanlang.lyxrica_springb.model.Review;
import com.vanlang.lyxrica_springb.model.User;
import com.vanlang.lyxrica_springb.request.ReviewRequest;

import java.util.List;

public interface ReviewService {
    public Review createReview(ReviewRequest req, User user) throws ProductException;
    public List<Review> getAllReviews(Long productId);
}
