package com.gozluketicaret.demo.controller;

import com.gozluketicaret.demo.model.User;


import jakarta.servlet.http.HttpSession;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LogoutController {

    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(LogoutController.class);


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user != null) {
            logger.info("Kullanıcı çıkış yaptı: {}", user.getUsername());
        } else {
            logger.info("Oturum olmadan çıkış işlemi.");
        }

        session.invalidate();  // oturumu temizle
        return "redirect:/login?logout";
    }
}
