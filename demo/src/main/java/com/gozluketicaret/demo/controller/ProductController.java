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

    // Ürünler sayfası
    @GetMapping("/products")
    public String showProducts(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "products"; // HTML sayfasının adı
    }

    // Admin ürün listesi sayfası
    @GetMapping("/admin/products/list")
    public String showProductList(Model model) {
        // Ürünleri veritabanından al
        List<Product> products = productRepository.findAll();

        // Toplam stok hesapla
        int totalStock = products.stream().mapToInt(Product::getStock).sum();

        // Model'e verileri ekle
        model.addAttribute("products", products);
        model.addAttribute("totalStock", totalStock);
        model.addAttribute("productCount", products.size());

        // Şablonu döndür
        return "admin-product-list"; // HTML şablonunun adı
    }
}