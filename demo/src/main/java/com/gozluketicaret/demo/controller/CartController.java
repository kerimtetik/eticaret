package com.gozluketicaret.demo.controller;

import com.gozluketicaret.demo.service.CartService;
import com.gozluketicaret.demo.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // DÜZGÜN Model importu!
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired 
    private CartService cartService;
    
    @GetMapping
    public String showCartPage(Model model, @RequestParam(required = false) Long userId) {
        if (userId != null) {
            List<CartItem> items = cartService.getCartItems(userId);
            model.addAttribute("cartItems", items);
        }
        return "cart"; // cart.html view döner
    }

    @PostMapping("/add")
    @ResponseBody
    public void addToCart(@RequestParam Long userId, @RequestParam Long productId, @RequestParam int quantity) {
        cartService.addProductToCart(userId, productId, quantity);
    }

    @PostMapping("/update")
    @ResponseBody
    public void updateQuantity(@RequestParam Long cartItemId, @RequestParam int quantity) {
        cartService.updateQuantity(cartItemId, quantity);
    }

    @PostMapping("/remove")
    @ResponseBody
    public void removeItem(@RequestParam Long cartItemId) {
        cartService.removeItem(cartItemId);
    }

    @GetMapping("/items")
    @ResponseBody
    public List<CartItem> getCartItems(@RequestParam Long userId) {
        return cartService.getCartItems(userId);
    }
}
