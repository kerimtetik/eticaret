package com.gozluketicaret.demo.controller;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.gozluketicaret.demo.Product;
import com.gozluketicaret.demo.repository.ProductRepository;
import com.gozluketicaret.demo.service.ProductService;

import org.springframework.ui.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@Controller
public class ProductController {

	    @Autowired
	    private ProductService productService;
	    @Autowired
	    private ProductRepository productRepository;
	    
	    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	    
	    


	    @GetMapping("/products")
	    public String listProducts(
	            @RequestParam(required = false) List<String> brand,
	            @RequestParam(required = false) List<String> gender,
	            @RequestParam(required = false) Double minPrice,
	            @RequestParam(required = false) Double maxPrice,
	            Model model) {

	        List<Product> products = productRepository.findAll();

	        if (brand != null && !brand.isEmpty()) {
	            products = products.stream()
	                    .filter(p -> brand.contains(p.getBrand()))
	                    .collect(Collectors.toList());
	        }

	        if (gender != null && !gender.isEmpty()) {
	            products = products.stream()
	                    .filter(p -> gender.contains(p.getGender()))
	                    .collect(Collectors.toList());
	        }


	        if (minPrice != null) {
	            products = products.stream()
	                    .filter(p -> p.getPrice() >= minPrice)
	                    .collect(Collectors.toList());
	        }

	        if (maxPrice != null) {
	            products = products.stream()
	                    .filter(p -> p.getPrice() <= maxPrice)
	                    .collect(Collectors.toList());
	        }

	        // Tüm markaları filtre menüsünde göstermek için
	        List<String> allBrands = products.stream()
	                .map(Product::getBrand)
	                .filter(Objects::nonNull)
	                .distinct()
	                .collect(Collectors.toList());

	        model.addAttribute("products", products);
	        model.addAttribute("allBrands", allBrands);
	        model.addAttribute("selectedBrands", brand); // checkbox işaretlensin
	        logger.info("Urunler listelendi");
	        return "products";
	        
	    }

	    // Admin paneli: tüm ürünler ve stok sayısı
	    @GetMapping("/admin/products/list")
	    public String showProductList(Model model) {
	        List<Product> products = productService.getAllProducts();
	        int totalStock = products.stream().mapToInt(Product::getStock).sum();

	        model.addAttribute("products", products);
	        model.addAttribute("totalStock", totalStock);
	        model.addAttribute("productCount", products.size());

	        return "admin-product-list";
	    }
	    @GetMapping("/product/{id}")
	    public String getProductDetail(@PathVariable Long id, Model model) {
	        Product product = productRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Ürün bulunamadı"));
	        model.addAttribute("product", product);
	        return "product-detail"; // .html dosyası
	    }

}

