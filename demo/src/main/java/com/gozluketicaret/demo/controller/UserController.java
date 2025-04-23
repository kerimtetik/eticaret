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
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody User user) {
        try {
            userRepository.save(user);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Kullanıcı başarıyla kaydedildi.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Kayıt sırasında bir hata oluştu: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}


