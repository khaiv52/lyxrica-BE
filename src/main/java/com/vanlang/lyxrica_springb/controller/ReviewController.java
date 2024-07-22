package com.vanlang.lyxrica_springb.controller;

import com.vanlang.lyxrica_springb.exception.ProductException;
import com.vanlang.lyxrica_springb.exception.UserException;
import com.vanlang.lyxrica_springb.model.Review;
import com.vanlang.lyxrica_springb.model.User;
import com.vanlang.lyxrica_springb.request.ReviewRequest;
import com.vanlang.lyxrica_springb.service.ReviewService;
import com.vanlang.lyxrica_springb.service.UserService;
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
