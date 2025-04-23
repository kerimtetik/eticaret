package com.gozluketicaret.demo.controller;

import com.gozluketicaret.demo.model.User;
import com.gozluketicaret.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model, RedirectAttributes redirectAttributes) {
        // Kullanıcı adı ve şifreyi kontrol et
        User user = userService.findByUsernameAndPassword(username, password);
        
        if (user != null) {
            // Giriş başarılı, ana sayfaya yönlendir
            return "redirect:/home"; // Ana sayfa adresi
        } else {
            // Hatalı giriş, hata mesajı ile geri dön
            redirectAttributes.addFlashAttribute("error", "Geçersiz kullanıcı adı veya şifre");
            return "redirect:/login"; // Giriş sayfasına geri dön
        }
    }
}