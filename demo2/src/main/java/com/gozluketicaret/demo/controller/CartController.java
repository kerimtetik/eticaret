package com.gozluketicaret.demo.controller;

import com.gozluketicaret.demo.CartItem;

import com.gozluketicaret.demo.Product;
import com.gozluketicaret.demo.model.User;
import com.gozluketicaret.demo.repository.ProductRepository;
import com.gozluketicaret.demo.service.CartService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired 
    private CartService cartService;

    @Autowired
    private ProductRepository productRepository;
    
    private static final Logger logger = LoggerFactory.getLogger(CartController.class);


    // Sepet sayfasını gösterir
    
    @GetMapping
    public String showCartPage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user != null) {
            List<CartItem> items = cartService.getCartItems(user.getId());
            model.addAttribute("cartItems", items);

            double total = cartService.getCartTotal(user.getId());
            model.addAttribute("cartTotal", total);
        }
        logger.info("Sepet gosterildi");

        return "cart";
    }


    // Sepete ürün ekler
    @PostMapping("/add")
    public String sepeteEkle(@RequestParam Long productId, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
        	logger.info("Sepete urun eklendi");
            cartService.addToCart(user, product, 1); // adeti 1 varsayıldı
        }

        return "redirect:/cart";
    }

    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<String> updateQuantity(@RequestParam Long cartItemId, @RequestParam int quantity) {
        try {
            cartService.updateQuantity(cartItemId, quantity);
            return ResponseEntity.ok("Güncellendi");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Hata oluştu");
        }
    }


    // Sepet öğesini kaldırır
    @PostMapping("/remove")
    @ResponseBody
    public ResponseEntity<String> removeItem(@RequestParam Long cartItemId) {
        try {
            cartService.removeItem(cartItemId);
            logger.info("Sepetten urun silindi");
            return ResponseEntity.ok("Silindi");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Hata oluştu");
        }
    }


    // Sepet öğelerini (JSON olarak) getirir
    @GetMapping("/items")
    @ResponseBody
    public ResponseEntity<List<CartItem>> getCartItems(HttpSession session) {
        try {
            User user = (User) session.getAttribute("loggedInUser");
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
            List<CartItem> cartItems = cartService.getCartItems(user.getId());
            return ResponseEntity.ok(cartItems);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
