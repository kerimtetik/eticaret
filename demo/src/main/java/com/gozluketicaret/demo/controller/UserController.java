package com.gozluketicaret.demo.controller;


import com.gozluketicaret.demo.model.User;
import com.gozluketicaret.demo.repository.UserRepository;
import com.gozluketicaret.demo.repository.VerificationTokenRepository;
import com.gozluketicaret.demo.service.EmailService;
import com.gozluketicaret.demo.model.VerificationToken;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private EmailService emailService;
    
    
    


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

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(false); // doğrulama gerekli
        userRepository.save(user);

        // Token oluştur ve mail gönder
        String token = java.util.UUID.randomUUID().toString();
        VerificationToken vt = new VerificationToken(token, user, LocalDateTime.now().plusHours(24));
        tokenRepository.save(vt);
        
        // Link oluşturuluyor
        String link = "http://localhost:8080/api/users/verify?token=" + token;
        System.out.println("Doğrulama linki: " + link); // Konsolda görelim


        emailService.send(user.getEmail(), "Hesabınızı doğrulayın", "Doğrulamak için tıklayın: " + link);

        
        response.put("message", "Kayıt başarılı. Lütfen e-postanızı doğrulayın.");
        return response;
    }

    @GetMapping("/verify")
    public String verifyUser(@RequestParam String token) {
        Optional<VerificationToken> optionalToken = tokenRepository.findByToken(token);

        if (optionalToken.isPresent()) {
            VerificationToken vt = optionalToken.get();

            if (vt.getExpiryDate().isAfter(LocalDateTime.now())) {
                User user = vt.getUser();
                user.setEnabled(true);
                userRepository.save(user);
                return "verified"; // örneğin: verified.html sayfası
            }
        }

        return "token_expired_or_invalid"; // örneğin: token_expired_or_invalid.html
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