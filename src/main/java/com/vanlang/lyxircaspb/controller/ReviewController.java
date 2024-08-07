package com.vanlang.lyxircaspb.controller;

import com.vanlang.lyxircaspb.exception.ProductException;
import com.vanlang.lyxircaspb.exception.UserException;
import com.vanlang.lyxircaspb.model.Review;
import com.vanlang.lyxircaspb.model.User;
import com.vanlang.lyxircaspb.request.ReviewRequest;
import com.vanlang.lyxircaspb.service.ReviewService;
import com.vanlang.lyxircaspb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Review> createReview(@RequestBody ReviewRequest req, @RequestHeader("Authorization") String jwt) throws UserException, ProductException {
        User user = userService.findUserProfileByJwt(jwt);
        Review review = reviewService.createReview(req, user);

        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getProductReviews(@PathVariable Long productId) throws UserException, ProductException {
        List<Review> reviews = reviewService.getAllReviews(productId);
        return new ResponseEntity<>(reviews, HttpStatus.ACCEPTED);
    }
}
