package com.gozluketicaret.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model; // ← doğru import bu!

import com.gozluketicaret.demo.Address;
import com.gozluketicaret.demo.Order;
import com.gozluketicaret.demo.repository.AddressRepository;
import com.gozluketicaret.demo.repository.OrderRepository;

@Controller
public class OrderSummaryController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AddressRepository addressRepository;

    @GetMapping("/order/summary/{orderId}")
    public String showSummary(@PathVariable Long orderId, Model model) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        Address address = addressRepository.findById(order.getAddressId()).orElse(null);

        model.addAttribute("order", order);
        model.addAttribute("address", address);

        return "order-summary";
    }
}


