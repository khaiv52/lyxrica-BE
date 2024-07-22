package com.vanlang.lyxrica_springb.service;

import com.vanlang.lyxrica_springb.exception.OrderException;
import com.vanlang.lyxrica_springb.exception.ProductException;
import com.vanlang.lyxrica_springb.model.Address;
import com.vanlang.lyxrica_springb.model.Order;
import com.vanlang.lyxrica_springb.model.User;

import java.util.List;

public interface OrderService {
    public Order createOrder(User user, Address shippingAddress) throws ProductException;

    public Order findOrderById(Long orderId) throws OrderException;

    public List<Order> usersOrderHistory(Long userId) throws OrderException;

    public Order placeOrder(Long orderId) throws OrderException;

    public Order confirmedOrder(Long orderId) throws OrderException;

    public Order shippedOrder(Long orderId) throws OrderException;

    public Order deliveredOrder(Long orderId) throws OrderException;

    public List<Order> getAllOrders() throws OrderException;

    public Order canceledOrder(Long orderId) throws OrderException;

    public String deleteOrder(Long orderId) throws OrderException;
}
