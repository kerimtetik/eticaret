package com.gozluketicaret.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gozluketicaret.demo.Cart;
import com.gozluketicaret.demo.CartItem;
import com.gozluketicaret.demo.Product;
import com.gozluketicaret.demo.model.User;  // User modelini eklemelisiniz.
import com.gozluketicaret.demo.repository.CartItemRepository;
import com.gozluketicaret.demo.repository.CartRepository;
import com.gozluketicaret.demo.repository.ProductRepository;
import com.gozluketicaret.demo.repository.UserRepository;  // UserRepository'i de ekleyin.

@Service
public class CartService {

    @Autowired private CartRepository cartRepository;
    @Autowired private CartItemRepository cartItemRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private UserRepository userRepository;  // Kullanıcı doğrulaması için
    
    

    public Cart getOrCreateCart(Long userId) {
        // Kullanıcı doğrulaması
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("Kullanıcı girişi yapılmamış!"));

        return cartRepository.findByUserIdAndIsCheckedOutFalse(userId)
            .orElseGet(() -> {
                Cart cart = new Cart();
                
                return cartRepository.save(cart);
            });
    }

    public void addProductToCart(Long userId, Long productId, int quantity) {
        // Kullanıcı doğrulaması
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("Kullanıcı girişi yapılmamış!"));
        
        Cart cart = getOrCreateCart(userId);
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalStateException("Ürün bulunamadı"));

        // Sepette ürün var mı kontrol et
        for (CartItem item : cart.getItems()) {
            if (item.getProduct().getId().equals(productId)) {
                item.setQuantity(item.getQuantity() + quantity);
                cartItemRepository.save(item);
                return;
            }
        }

        // Yeni ürün ekle
        CartItem newItem = new CartItem();
        newItem.setCart(cart);
        newItem.setProduct(product);
        newItem.setQuantity(quantity);
        cart.getItems().add(newItem);

        cartRepository.save(cart);
    }

    public void updateQuantity(Long cartItemId, int quantity) {
        CartItem item = cartItemRepository.findById(cartItemId).orElseThrow(() -> new IllegalStateException("Sepet öğesi bulunamadı"));
        item.setQuantity(quantity);
        cartItemRepository.save(item);
    }

    public void removeItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    public List<CartItem> getCartItems(Long userId) {
        // Kullanıcı doğrulaması
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("Kullanıcı girişi yapılmamış!"));
        
        return getOrCreateCart(userId).getItems();
    }
}
