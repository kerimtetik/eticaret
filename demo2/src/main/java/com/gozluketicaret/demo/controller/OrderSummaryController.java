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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Controller
public class OrderSummaryController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AddressRepository addressRepository;
    
    private static final Logger logger = LoggerFactory.getLogger(OrderSummaryController.class);


    @GetMapping("/order/summary/{orderId}")
    public String showSummary(@PathVariable Long orderId, Model model) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        Address address = addressRepository.findById(order.getAddressId()).orElse(null);

        model.addAttribute("order", order);
        model.addAttribute("address", address);
        logger.info("Siparis verildi");
        return "order-summary";
    }
}


