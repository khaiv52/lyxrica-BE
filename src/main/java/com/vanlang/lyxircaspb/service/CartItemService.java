package com.vanlang.lyxircaspb.service;

import com.vanlang.lyxircaspb.exception.CartItemException;
import com.vanlang.lyxircaspb.exception.UserException;
import com.vanlang.lyxircaspb.model.Cart;
import com.vanlang.lyxircaspb.model.CartItem;
import com.vanlang.lyxircaspb.model.Product;

public interface CartItemService {

    public CartItem createCartItem(CartItem cartItem);

    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException;

    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId);

    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException;

    public CartItem findCartItemById(Long cartItemId) throws CartItemException;
}
