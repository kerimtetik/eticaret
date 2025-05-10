package com.gozluketicaret.demo.controller;


import com.gozluketicaret.demo.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import com.gozluketicaret.demo.repository.UserRepository;
import com.gozluketicaret.demo.repository.VerificationTokenRepository;
import com.gozluketicaret.demo.service.EmailService;
import com.gozluketicaret.demo.model.VerificationToken;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

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
    
    
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/register")
    public Map<String, Object> registerUser(@RequestBody User user) {
        logger.info("Kayıt isteği alındı: {}", user.getUsername());

        Map<String, Object> response = new HashMap<>();

        if (userRepository.existsByUsername(user.getUsername())) {
            logger.warn("Kullanıcı adı zaten kullanılıyor: {}", user.getUsername());
            response.put("message", "Kullanıcı adı zaten kullanılıyor.");
            return response;
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            logger.warn("E-posta zaten kayıtlı: {}", user.getEmail());
            response.put("message", "E-posta adresi zaten kayıtlı.");
            return response;
        }

        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setEnabled(false);
            userRepository.save(user);
            logger.debug("Kullanıcı veritabanına kaydedildi: {}", user.getUsername());

            String token = UUID.randomUUID().toString();
            VerificationToken vt = new VerificationToken(token, user, LocalDateTime.now().plusHours(24));
            tokenRepository.save(vt);
            logger.debug("Doğrulama tokeni oluşturuldu: {}", token);

            String link = "http://localhost:8080/api/users/verify?token=" + token;
            emailService.send(user.getEmail(), "Hesabınızı doğrulayın", "Doğrulamak için tıklayın: " + link);
            logger.info("Doğrulama e-postası gönderildi: {}", user.getEmail());

            response.put("message", "Kayıt başarılı. Lütfen e-postanızı doğrulayın.");
        } catch (Exception e) {
            logger.error("Kayıt sırasında hata: {}", e.getMessage(), e);
            response.put("message", "Kayıt başarısız.");
        }

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