package com.vanlang.lyxrica_springb.service;

import com.vanlang.lyxrica_springb.exception.CartItemException;
import com.vanlang.lyxrica_springb.exception.UserException;
import com.vanlang.lyxrica_springb.model.Cart;
import com.vanlang.lyxrica_springb.model.CartItem;
import com.vanlang.lyxrica_springb.model.Product;
import jdk.jshell.spi.ExecutionControl;

public interface CartItemService {

    public CartItem createCartItem(CartItem cartItem);

    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException;

    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId);

    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException;

    public CartItem findCartItemById(Long cartItemId) throws CartItemException;
}
