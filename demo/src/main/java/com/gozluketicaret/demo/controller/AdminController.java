package com.gozluketicaret.demo.controller;

import com.gozluketicaret.demo.Product;
import com.gozluketicaret.demo.model.User;
import com.gozluketicaret.demo.repository.ProductRepository;
import com.gozluketicaret.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    // Admin Panel Ana Sayfa
    @GetMapping("/dashboard")
    public String dashboard() {
        return "admin/dashboard"; // admin paneli ana sayfa
    }

    // Ürün Ekleme Sayfası
    @GetMapping("/products/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "admin/add-product";
    }

    // Ürün Ekleme İşlemi
    @PostMapping("/products/add")
    public String addProduct(@ModelAttribute Product product) {
        productRepository.save(product);
        return "redirect:/admin/products";
    }

    // Ürünleri Listele
    @GetMapping("/products")
    public String listProducts(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "admin-product-list";
    }

    // Kullanıcıları Listele
    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> users = (List<User>) userRepository.findAll();
        model.addAttribute("users", users);
        return "admin/user-list";
    }
}
