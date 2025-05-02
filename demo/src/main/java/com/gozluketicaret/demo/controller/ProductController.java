package com.gozluketicaret.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.gozluketicaret.demo.Product;
import com.gozluketicaret.demo.repository.ProductRepository;
import com.gozluketicaret.demo.service.ProductService;

import org.springframework.ui.Model;


@Controller
public class ProductController {

	    @Autowired
	    private ProductService productService;
	    @Autowired
	    private ProductRepository productRepository;


	    // Ürünler sayfası (filtreli veya tam liste)
	    @GetMapping("/products")
	    public String showProducts(@RequestParam(required = false) String gender, Model model) {
	        List<Product> products;

	        if (gender != null) {
	            products = productService.getProductsByGender(gender); // filtreli
	        } else {
	            products = productService.getAllProducts(); // tüm ürünler
	        }

	        model.addAttribute("products", products);
	        return "products"; // products.html
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

