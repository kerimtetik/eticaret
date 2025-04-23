package com.gozluketicaret.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gozluketicaret.demo.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
