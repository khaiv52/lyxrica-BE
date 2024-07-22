package com.vanlang.lyxrica_springb.controller;

import com.vanlang.lyxrica_springb.exception.ProductException;
import com.vanlang.lyxrica_springb.exception.UserException;
import com.vanlang.lyxrica_springb.model.Cart;
import com.vanlang.lyxrica_springb.model.User;
import com.vanlang.lyxrica_springb.reponse.ApiResponse;
import com.vanlang.lyxrica_springb.request.AddItemRequest;
import com.vanlang.lyxrica_springb.service.CartService;
import com.vanlang.lyxrica_springb.service.UserService;
import jakarta.persistence.PreUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;

    @GetMapping()
//    @ManagedOperation(description = "find cart by user id")
    public ResponseEntity<Cart> findCartByUserId(@RequestHeader("Authorization") String jwt) throws UserException, ProductException {
        User user = userService.findUserProfileByJwt(jwt);
        Cart cart = cartService.findUserCart(user.getId());

        return new ResponseEntity<Cart>(cart, HttpStatus.OK);
    }

    @PostMapping("/add")
//    @ManagedOperation(description = "add item to cart")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestBody AddItemRequest req, @RequestHeader("Authorization") String jwt) throws UserException, ProductException {
        User user = userService.findUserProfileByJwt(jwt);

        cartService.addCartItem(user.getId(), req);
//        System.out.println(req.getQuantity());

        ApiResponse res = new ApiResponse();
        res.setMessage("Item added to cart");
        res.setStatus(true);
        return new ResponseEntity(res, HttpStatus.OK);
    }
}
