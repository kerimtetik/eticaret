package com.gozluketicaret.demo.controller;

//ProductSearchController.java


import com.gozluketicaret.demo.Product;
import com.gozluketicaret.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Controller
public class ProductSearchController {

 @Autowired
 private ProductRepository productRepository;
 private static final Logger logger = LoggerFactory.getLogger(ProductSearchController.class);


 @GetMapping("/search")
 public String searchProducts(@RequestParam("query") String query, Model model) {
     // Küçük harfe çevirerek eşleştirme yapılır
     String q = query.toLowerCase();

     List<Product> results = productRepository.findAll().stream()
             .filter(p ->
                     (p.getGender() != null && p.getGender().toLowerCase().contains(q)) ||
                     (p.getBrand() != null && p.getBrand().toLowerCase().contains(q)) ||
                     (p.getName() != null && p.getName().toLowerCase().contains(q)) ||
                     (p.getProduct_code() != null && p.getProduct_code().toLowerCase().contains(q))
             )
             .toList();

     model.addAttribute("products", results);
     model.addAttribute("query", query);
     logger.info("Urunler listelendi");
     return "search-results";  // gösterilecek HTML
 }
}

