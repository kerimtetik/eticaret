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

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired 
    private CartService cartService;

    @Autowired
    private ProductRepository productRepository;

    // Sepet sayfasını gösterir
    @GetMapping
    public String showCartPage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user != null) {
            List<CartItem> items = cartService.getCartItems(user.getId());
            model.addAttribute("cartItems", items);
        }
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
            cartService.addToCart(user, product, 1); // adeti 1 varsayıldı
        }

        return "redirect:/cart";
    }

    // Sepet öğesinin miktarını günceller
    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<String> updateQuantity(@RequestParam Long cartItemId, @RequestParam int quantity) {
        try {
            if (quantity <= 0) {
                return ResponseEntity.badRequest().body("Adet 0 veya daha küçük olamaz.");
            }
            cartService.updateQuantity(cartItemId, quantity);
            return ResponseEntity.ok("Sepet öğesi güncellendi.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Hata oluştu.");
        }
    }

    // Sepet öğesini kaldırır
    @PostMapping("/remove")
    @ResponseBody
    public ResponseEntity<String> removeItem(@RequestParam Long cartItemId) {
        try {
            cartService.removeItem(cartItemId);
            return ResponseEntity.ok("Ürün sepetten çıkarıldı.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Hata oluştu.");
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
