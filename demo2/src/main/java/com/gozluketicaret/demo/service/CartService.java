package com.gozluketicaret.demo.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gozluketicaret.demo.Cart;
import com.gozluketicaret.demo.CartItem;
import com.gozluketicaret.demo.Product;
import com.gozluketicaret.demo.model.User;
import com.gozluketicaret.demo.repository.CartItemRepository;
import com.gozluketicaret.demo.repository.CartRepository;
import com.gozluketicaret.demo.repository.ProductRepository;
import com.gozluketicaret.demo.repository.UserRepository;

@Service
public class CartService {

    @Autowired private CartRepository cartRepository;
    @Autowired private CartItemRepository cartItemRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private UserRepository userRepository;
    
    
    

    // Sepet oluşturma ya da var olanı alma
    public Cart getOrCreateCart(Long userId) {
        return cartRepository.findByUser_Id(userId)
            .orElseGet(() -> {
                Cart newCart = new Cart();
                User user = userRepository.findById(userId)
                              .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
                newCart.setUser(user);
                return cartRepository.save(newCart);
            });
    }

    public void addToCart(User user, Product product, int quantity) {
        Cart cart = getOrCreateCart(user.getId()); // kullanıcıya ait sepet alınır veya oluşturulur

        for (CartItem item : cart.getItems()) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.setQuantity(item.getQuantity() + quantity);
                cartItemRepository.save(item);
                return;
            }
        }


        CartItem newItem = new CartItem();
        newItem.setCart(cart);  // kullanıcı ilişkisi buradan kurulmuş oluyor
        newItem.setProduct(product);
        newItem.setQuantity(quantity);

        cart.getItems().add(newItem); // cart'a ekleniyor
        cartRepository.save(cart);    // cascade ile item da kaydedilir
    }


    // Sepet öğesinin miktarını güncelleme
    public void updateQuantity(Long cartItemId, int quantity) {
        CartItem item = cartItemRepository.findById(cartItemId).orElseThrow(() -> new IllegalStateException("Sepet öğesi bulunamadı"));
        item.setQuantity(quantity);
        cartItemRepository.save(item);
    }

    // Sepet öğesini silme
    public void removeItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    // Kullanıcının sepetindeki ürünleri almak
    public List<CartItem> getCartItems(Long userId) {
        // Kullanıcı doğrulaması
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("Kullanıcı girişi yapılmamış!"));
        
        return getOrCreateCart(userId).getItems();
    }

    // Sepeti temizle (isteğe bağlı)
    public void clearCart(Long userId) {
        Cart cart = getOrCreateCart(userId);
        cart.getItems().clear();
        cartRepository.save(cart);
    }
    public double getCartTotal(Long userId) {
        List<CartItem> items = getCartItems(userId);
        double total = 0.0;

        for (CartItem item : items) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }

        return total;
    }


}
