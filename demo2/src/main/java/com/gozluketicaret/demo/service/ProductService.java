package com.gozluketicaret.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gozluketicaret.demo.Product;
import com.gozluketicaret.demo.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getProductsByGenderList(List<String> genders) {
        return productRepository.findByGenderIn(genders);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
