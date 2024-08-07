package com.vanlang.lyxircaspb.controller;

import com.vanlang.lyxircaspb.exception.CartItemException;
import com.vanlang.lyxircaspb.exception.UserException;
import com.vanlang.lyxircaspb.model.CartItem;
import com.vanlang.lyxircaspb.model.User;
import com.vanlang.lyxircaspb.reponse.ApiResponse;
import com.vanlang.lyxircaspb.service.CartItemService;
import com.vanlang.lyxircaspb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart_item")
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private UserService userService;

    @DeleteMapping("/{cartItemId}")
    @ManagedOperation(description = "Remove Cart Item From Cart")
    public ResponseEntity<ApiResponse> removeCartItem(@PathVariable Long cartItemId, @RequestHeader("Authorization") String jwt) throws UserException, CartItemException {
        User user = userService.findUserProfileByJwt(jwt);
        cartItemService.removeCartItem(user.getId(), cartItemId);

        ApiResponse res = new ApiResponse();
        res.setMessage("Delete item from cart");
        res.setStatus(true);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/{cartItemId}")
    @ManagedOperation(description = "Updated Cart Item From Cart")
    public ResponseEntity<ApiResponse> updatedCartItem(@PathVariable("cartItemId") Long cartItemId, @RequestHeader("Authorization") String jwt, @RequestBody CartItem cartItem) throws UserException, CartItemException {
        User user = userService.findUserProfileByJwt(jwt);
        cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);

        ApiResponse res = new ApiResponse();
        res.setMessage("Updated item from cart");
        res.setStatus(true);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
