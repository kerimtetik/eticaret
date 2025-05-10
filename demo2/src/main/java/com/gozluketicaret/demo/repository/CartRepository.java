package com.gozluketicaret.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gozluketicaret.demo.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
    
    Optional<Cart> findByUser_Id(Long userId); // ✨ Doğrusu bu şekilde
}
