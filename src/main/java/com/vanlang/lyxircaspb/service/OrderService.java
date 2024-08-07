package com.vanlang.lyxircaspb.service;

import com.vanlang.lyxircaspb.exception.OrderException;
import com.vanlang.lyxircaspb.exception.ProductException;
import com.vanlang.lyxircaspb.model.Address;
import com.vanlang.lyxircaspb.model.Order;
import com.vanlang.lyxircaspb.model.User;

import java.util.List;

public interface OrderService {
    public Order createOrder(User user, Address shippingAddress) throws ProductException;

    public Order findOrderById(Long orderId) throws OrderException;

    public List<Order> usersOrderHistory(Long userId) throws OrderException;

    public Order placeOrder(Long orderId) throws OrderException;

    public Order confirmedOrder(Long orderId) throws OrderException;

    public Order shippedOrder(Long orderId) throws OrderException;

    public Order deliveringOrder(Long orderId) throws OrderException;

    public Order deliveredOrder(Long orderId) throws OrderException;

    public List<Order> getAllOrders() throws OrderException;

    public Order canceledOrder(Long orderId) throws OrderException;

    public String deleteOrder(Long orderId) throws OrderException;

    public List<Order> getOrdersByStatus(Long userId, String orderStatus) throws OrderException;
}
