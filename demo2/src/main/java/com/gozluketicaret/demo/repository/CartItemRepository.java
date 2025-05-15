package com.gozluketicaret.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gozluketicaret.demo.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	List<CartItem> findByCart_User_Id(Long userId); // doğru olan
    void deleteByCart_User_Id(Long userId);         // doğru olan
}
