package com.gozluketicaret.demo.controller;

import com.gozluketicaret.demo.model.User;
import com.gozluketicaret.demo.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(value = "error", required = false) String error,
                                 Model model) {
        if (error != null) {
            model.addAttribute("error", "Kullanıcı adı veya şifre hatalı!");
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        logger.info("Giriş denemesi: {}", username);

        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (!user.isEnabled()) {
                logger.warn("E-posta doğrulanmamış giriş denemesi: {}", username);
                model.addAttribute("error", "E-posta doğrulaması yapılmamış.");
                return "login";
            }

            if (passwordEncoder.matches(password, user.getPassword())) {
                session.setAttribute("loggedInUser", user);
                logger.info("Giriş başarılı: {}", username);
                return "redirect:/home";
            }
        }

        logger.warn("Giriş başarısız: {}", username);
        model.addAttribute("error", "Kullanıcı adı veya şifre hatalı.");
        return "login";
    }
}
