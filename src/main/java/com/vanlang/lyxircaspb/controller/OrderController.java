package com.vanlang.lyxircaspb.controller;

import com.vanlang.lyxircaspb.exception.OrderException;
import com.vanlang.lyxircaspb.exception.ProductException;
import com.vanlang.lyxircaspb.exception.UserException;
import com.vanlang.lyxircaspb.model.Address;
import com.vanlang.lyxircaspb.model.Order;
import com.vanlang.lyxircaspb.model.User;
import com.vanlang.lyxircaspb.reponse.ApiResponse;
import com.vanlang.lyxircaspb.service.OrderItemService;
import com.vanlang.lyxircaspb.service.OrderService;
import com.vanlang.lyxircaspb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/orderStatus")
    public ResponseEntity<List<Order>> getOrders(@RequestParam(required = false) String orderStatus, @RequestHeader("Authorization") String jwt) throws OrderException, UserException {
        User user = userService.findUserProfileByJwt(jwt);
        List<Order> orders = orderService.getOrdersByStatus(user.getId(), orderStatus);
        return ResponseEntity.ok(orders);
    }
}
