package com.gozluketicaret.demo.controller;


import com.gozluketicaret.demo.model.User;
import com.gozluketicaret.demo.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") // frontend'in erişebilmesi için
public class UserController {

	 @Autowired
	    private UserRepository userRepository;

	    @PostMapping("/register")
	    public Map<String, Object> registerUser(@RequestBody User user) {
	        Map<String, Object> response = new HashMap<>();

	        if (userRepository.existsByUsername(user.getUsername())) {
	            response.put("message", "Kullanıcı adı zaten kullanılıyor.");
	            return response;
	        }

	        if (userRepository.existsByEmail(user.getEmail())) {
	            response.put("message", "E-posta adresi zaten kayıtlı.");
	            return response;
	        }

	        userRepository.save(user);
	        response.put("message", "Kayıt başarılı");
	        return response;
	    }

	    @GetMapping("/check-username")
	    public Map<String, Boolean> checkUsername(@RequestParam String username) {
	        boolean exists = userRepository.existsByUsername(username);
	        return Map.of("exists", exists);
	    }

	    @GetMapping("/check-email")
	    public Map<String, Boolean> checkEmail(@RequestParam String email) {
	        boolean exists = userRepository.existsByEmail(email);
	        return Map.of("exists", exists);
	    }
	}


