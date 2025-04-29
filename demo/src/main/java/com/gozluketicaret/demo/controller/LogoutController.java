package com.gozluketicaret.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class LogoutController {

	@GetMapping("/logout")
	public String logout(HttpSession session) {
	    session.invalidate();  // oturumu temizle
	    return "redirect:/login";
	}
}
