package com.vanlang.lyxircaspb.service;

import com.vanlang.lyxircaspb.exception.ProductException;
import com.vanlang.lyxircaspb.model.Rating;
import com.vanlang.lyxircaspb.model.User;
import com.vanlang.lyxircaspb.request.RatingRequest;

import java.util.List;

public interface RatingService {
    public Rating createRating(RatingRequest req, User user) throws ProductException;
    public List<Rating> getProductRatings(Long productId) throws ProductException;
}
