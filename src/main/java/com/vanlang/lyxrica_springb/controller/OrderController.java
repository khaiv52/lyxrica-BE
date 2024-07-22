package com.vanlang.lyxrica_springb.controller;

import com.vanlang.lyxrica_springb.exception.OrderException;
import com.vanlang.lyxrica_springb.exception.ProductException;
import com.vanlang.lyxrica_springb.exception.UserException;
import com.vanlang.lyxrica_springb.model.Address;
import com.vanlang.lyxrica_springb.model.Order;
import com.vanlang.lyxrica_springb.model.User;
import com.vanlang.lyxrica_springb.reponse.ApiResponse;
import com.vanlang.lyxrica_springb.service.OrderItemService;
import com.vanlang.lyxrica_springb.service.OrderService;
import com.vanlang.lyxrica_springb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/orders")
public class    OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderItemService orderItemService;

    @PostMapping()
    public ResponseEntity<Order> createOrder(@RequestBody Address shippingAddress, @RequestHeader("Authorization") String jwt) throws UserException, ProductException {
        User user = userService.findUserProfileByJwt(jwt);

        Order order = orderService.createOrder(user, shippingAddress);
        System.out.println("Order " + order);
        return new ResponseEntity<Order>(order,HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Order>> userOrderHistory(@RequestHeader("Authorization") String jwt) throws UserException, OrderException {
        User user = userService.findUserProfileByJwt(jwt);
        List<Order> orders = orderService.usersOrderHistory(user.getId());

        return new ResponseEntity<>(orders,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> findOrderById(@PathVariable("id") Long orderId, @RequestHeader("Authorization") String jwt) throws UserException, OrderException {
        User user = userService.findUserProfileByJwt(jwt);

        Order order = orderService.findOrderById(orderId);
        return new ResponseEntity<>(order,HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteOrderById(@PathVariable("id") Long orderId, @RequestHeader("Authorization") String jwt) throws UserException, OrderException {
        User user = userService.findUserProfileByJwt(jwt);

        orderService.deleteOrder(orderId);
        ApiResponse res = new ApiResponse();
        res.setStatus(true);
        res.setMessage("Product deleted successfully");
        return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
    }
}
