package com.gozluketicaret.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller 
public class aa {
	@GetMapping("/home")
	public String show() {
		return "home";
	}
	@GetMapping("/register")
	public String show1() {
		return "register";
	}
	@GetMapping("/login")
	public String show2() {
		return "login";
	}
	
}




