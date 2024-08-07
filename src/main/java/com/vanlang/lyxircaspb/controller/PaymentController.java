package com.vanlang.lyxircaspb.controller;

import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.vanlang.lyxircaspb.exception.OrderException;
import com.vanlang.lyxircaspb.model.Order;
import com.vanlang.lyxircaspb.reponse.ApiResponse;
import com.vanlang.lyxircaspb.reponse.PaymentLinkResponse;
import com.vanlang.lyxircaspb.repository.OrderRepository;
import com.vanlang.lyxircaspb.service.OrderService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PaymentController {

    @Value("${razorpay.api.key}")
    String apiKey;

    @Value("${razorpay.api.secret}")
    String apiSecret;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/payments/{orderId}")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException, RazorpayException {
        System.out.println("Creating payment link for order ID: " + orderId);

        // Lấy thông tin đơn hàng
        Order order = orderService.findOrderById(orderId);

        // In thông tin đơn hàng ra console
        System.out.println("Order details:");
        System.out.println("Order ID: " + order.getId());
        System.out.println("Total Price: " + order.getTotalPrice());
        System.out.println("User Name: " + order.getUser().getFirstName() + " " + order.getUser().getLastName());
        System.out.println("User Email: " + order.getUser().getEmail());
        System.out.println("Order Status: " + order.getOrderStatus());

        try {
            RazorpayClient razorpay = new RazorpayClient(apiKey, apiSecret);

            JSONObject paymentLinkRequest = new JSONObject();

            paymentLinkRequest.put("amount", order.getTotalPrice() * 100);
            paymentLinkRequest.put("currency", "USD");

            JSONObject customer = new JSONObject();
            customer.put("name", order.getUser().getFirstName());
            customer.put("email", order.getUser().getEmail());
            paymentLinkRequest.put("customer", customer);

            JSONObject notify = new JSONObject();
            notify.put("sms", true);
            notify.put("email", true);
            paymentLinkRequest.put("notify", notify);

            paymentLinkRequest.put("callback_url", "http://localhost:3000/payment/" + orderId); // front-end
            paymentLinkRequest.put("callback_method", "get");

            PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);

            String paymentLinkId = payment.get("id");
            String paymentLinkUrl = payment.get("short_url");

            PaymentLinkResponse res = new PaymentLinkResponse();
            res.setPayment_link_id(paymentLinkId);
            res.setPayment_link_url(paymentLinkUrl);

            System.out.println("Payment link created successfully: " + paymentLinkUrl);

            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("Error creating payment link: " + e.getMessage());
            throw new RazorpayException(e.getMessage());
        }
    }

    @GetMapping("/payments")
    public ResponseEntity<ApiResponse> redirect(@RequestParam(name = "payment_id") String paymentId, @RequestParam(name = "order_id") Long orderId) throws OrderException, RazorpayException {
        System.out.println("Processing payment redirect for payment ID: " + paymentId + " and order ID: " + orderId);
        Order order = orderService.findOrderById(orderId);
        RazorpayClient razorpay = new RazorpayClient(apiKey, apiSecret);
        try {
            Payment payment = razorpay.payments.fetch(paymentId);

            if ("captured".equals(payment.get("status"))) {
                order.getPaymentDetails().setPaymentId(paymentId);
                order.getPaymentDetails().setStatus("COMPLETED");
                order.setOrderStatus("PLACED");
                orderRepository.save(order);
            }

            ApiResponse res = new ApiResponse();
            res.setMessage("Your order has been placed");
            res.setStatus(true);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("Error processing payment redirect: " + e.getMessage());
            throw new RazorpayException(e.getMessage());
        }
    }
}
