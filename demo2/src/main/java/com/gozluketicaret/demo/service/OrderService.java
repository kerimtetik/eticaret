package com.gozluketicaret.demo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gozluketicaret.demo.CartItem;
import com.gozluketicaret.demo.Order;
import com.gozluketicaret.demo.OrderItem;
import com.gozluketicaret.demo.Product;
import com.gozluketicaret.demo.repository.CartItemRepository;
import com.gozluketicaret.demo.repository.OrderRepository;
import com.gozluketicaret.demo.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional // 🟢 BU ANOTASYONU EKLE
    public Order createOrder(Long userId, Long addressId) {
        List<CartItem> cartItems = cartItemRepository.findByCart_User_Id(userId);
        if (cartItems.isEmpty()) {
            throw new IllegalStateException("Sepet boş.");
        }

        Order order = new Order();
        order.setUserId(userId);
        order.setAddressId(addressId);
        order.setCreatedAt(LocalDateTime.now());

        double total = 0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem item : cartItems) {
            Product product = item.getProduct();
            int newStock = product.getStock() - item.getQuantity();

            if (newStock < 0) {
                throw new IllegalStateException("Yeterli stok yok: " + product.getName());
            }

            product.setStock(newStock);
            product.setOutOfStock(newStock == 0);
            productRepository.save(product); // stok güncellemesi

            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(product.getPrice());
            orderItem.setOrder(order);

            total += item.getQuantity() * product.getPrice();
            orderItems.add(orderItem);
        }

        order.setTotalAmount(total);
        order.setItems(orderItems);

        Order savedOrder = orderRepository.save(order);

        // Sepeti temizle
        cartItemRepository.deleteByCart_User_Id(userId);

        return savedOrder;
    }
}
