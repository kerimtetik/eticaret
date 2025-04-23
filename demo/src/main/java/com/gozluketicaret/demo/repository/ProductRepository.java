package com.gozluketicaret.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gozluketicaret.demo.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
}
