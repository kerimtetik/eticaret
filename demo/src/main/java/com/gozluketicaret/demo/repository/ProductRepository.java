package com.gozluketicaret.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gozluketicaret.demo.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product> findByGenderIn(List<String> genders);

	
}
