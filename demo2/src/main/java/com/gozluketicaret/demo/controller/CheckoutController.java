package com.gozluketicaret.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gozluketicaret.demo.Order;
import com.gozluketicaret.demo.service.OrderService;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/complete")
    public ResponseEntity<Map<String, Object>> completeCheckout(@RequestBody Map<String, Object> request) {
        Long userId = Long.valueOf(request.get("userId").toString());
        Long addressId = Long.valueOf(request.get("addressId").toString());

        Order order = orderService.createOrder(userId, addressId);

        Map<String, Object> response = new HashMap<>();
        response.put("orderId", order.getId());

        return ResponseEntity.ok(response);
    }
}