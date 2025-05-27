package com.gozluketicaret.demo.controller;

import com.gozluketicaret.demo.Order;
import com.gozluketicaret.demo.OrderItem;
import com.gozluketicaret.demo.Product;
import com.gozluketicaret.demo.ProductImage;
import com.gozluketicaret.demo.model.User;
import com.gozluketicaret.demo.repository.OrderItemRepository;
import com.gozluketicaret.demo.repository.OrderRepository;
import com.gozluketicaret.demo.repository.ProductRepository;
import com.gozluketicaret.demo.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private OrderItemRepository orderItemRepository;

    // ✅ Admin Panel Ana Sayfa (rol kontrolü ile)
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "/login";
        }

        model.addAttribute("admin", user);
        return "admin-dashboard";
    }
    

    @GetMapping("/orders/{orderId}")
    public String viewOrderDetails(@PathVariable Long orderId, HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "/login";
        }

        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
            return "redirect:/admin/orders"; // veya hata sayfası
        }

        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);

        model.addAttribute("order", order);
        model.addAttribute("items", orderItems);
        return "admin-order-details";
    }

    // ✅ Ürün Ekleme Sayfası (rol kontrolü ile)
    @GetMapping("/products/add")
    public String showAddProductForm(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "/login";
        }

        model.addAttribute("product", new Product());
        return "admin-add-product";
    }

    @PostMapping("/products/add")
    public String addProduct(@ModelAttribute Product product,
                             @RequestParam("imageUrls") List<String> imageUrls,
                             HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "/login";
        }

        List<ProductImage> imageList = imageUrls.stream()
                .map(url -> {
                    ProductImage img = new ProductImage();
                    img.setImageUrl(url);
                    img.setProduct(product);
                    return img;
                }).collect(Collectors.toList());

        product.setImages(imageList);
        productRepository.save(product);

        return "admin-dashboard";
    }

    // ✅ Ürünleri Listele (rol kontrolü ile)
    @GetMapping("/products")
    public String listProducts(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "/login";
        }

        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "admin-product-list";
    }

    // ✅ Kullanıcıları Listele (rol kontrolü ile)
    @GetMapping("/users")
    public String listUsers(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "/login";
        }

        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);

        model.addAttribute("users", users);
        return "admin-user-list";
    }
    @Autowired
    private OrderRepository orderRepository;

    // Siparişleri listele
    @GetMapping("/orders")
    public String listOrders(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "/login";
        }

        List<Order> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);
        return "admin-order-list";
    }
}
