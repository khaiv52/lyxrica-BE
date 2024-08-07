package com.vanlang.lyxircaspb.controller;

import com.vanlang.lyxircaspb.exception.ProductException;
import com.vanlang.lyxircaspb.exception.UserException;
import com.vanlang.lyxircaspb.model.Cart;
import com.vanlang.lyxircaspb.model.User;
import com.vanlang.lyxircaspb.reponse.ApiResponse;
import com.vanlang.lyxircaspb.request.AddItemRequest;
import com.vanlang.lyxircaspb.service.CartService;
import com.vanlang.lyxircaspb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
