package com.vanlang.lyxrica_springb.service;

import com.vanlang.lyxrica_springb.exception.ProductException;
import com.vanlang.lyxrica_springb.model.Cart;
import com.vanlang.lyxrica_springb.model.User;
import com.vanlang.lyxrica_springb.request.AddItemRequest;

public interface CartService {

    public Cart createCart(User user);

    public String addCartItem(Long userId, AddItemRequest req) throws ProductException;

    public Cart findUserCart(Long userId) throws ProductException;

}
