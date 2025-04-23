package com.gozluketicaret.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.gozluketicaret.demo.Product;
import com.gozluketicaret.demo.repository.ProductRepository;
import org.springframework.ui.Model;


@Controller
public class ProductController {

	 	@Autowired
	    private ProductRepository productRepository;

	    @GetMapping("/products")
	    public String showProducts(Model model) {
	        List<Product> products = productRepository.findAll();
	        model.addAttribute("products", products);
	        return "products"; // HTML sayfasının adı
	    }
}
