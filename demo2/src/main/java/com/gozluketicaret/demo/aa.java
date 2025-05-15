package com.gozluketicaret.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gozluketicaret.demo.model.User;
import com.gozluketicaret.demo.service.CartService;

import jakarta.servlet.http.HttpSession;

@Controller 
public class aa {
	@Autowired
    private CartService cartService;

    @GetMapping("/home")
    public String showHomePage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user != null) {
            List<CartItem> cartItems = cartService.getCartItems(user.getId());
            model.addAttribute("cartItems", cartItems);
            model.addAttribute("user", user);
        }
        return "home"; // templates/home.html
    }


	@GetMapping("/register")
	public String show1() {
		return "register";
	}
	
	@GetMapping("/contact")
	public String showcontact() {
		return "contact";
	}
	
	
}




