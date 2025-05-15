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

@Controller
public class ProductSearchController {

 @Autowired
 private ProductRepository productRepository;

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
     return "search-results";  // gösterilecek HTML
 }
}

