package com.gozluketicaret.demo.service;

import com.gozluketicaret.demo.model.User;
import com.gozluketicaret.demo.model.VerificationToken;
import com.gozluketicaret.demo.repository.UserRepository;
import com.gozluketicaret.demo.repository.VerificationTokenRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    // Kayıt işlemi
    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(false); // Başlangıçta email doğrulanmamış
        userRepository.save(user);

        // Token oluştur ve veritabanına kaydet
        String token = UUID.randomUUID().toString();
        VerificationToken vt = new VerificationToken(token, user, LocalDateTime.now().plusHours(24));
        tokenRepository.save(vt);

        // E-posta gönder
        String link = "http://localhost:8080/api/users/verify?token=" + token;
        emailService.send(user.getEmail(), "Hesabınızı doğrulayın", "Doğrulamak için tıklayın: " + link);

        return user;
    }

    // Giriş işlemi
    public User login(String username, String rawPassword) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (passwordEncoder.matches(rawPassword, user.getPassword()) && user.isEnabled()) {
                return user;
            }
        }
        return null;
    }

    // E-posta doğrulama
    public boolean verifyEmail(String token) {
        Optional<VerificationToken> optionalToken = tokenRepository.findByToken(token);
        if (optionalToken.isPresent()) {
            VerificationToken vt = optionalToken.get();
            if (vt.getExpiryDate().isAfter(LocalDateTime.now())) {
                User user = vt.getUser();
                user.setEnabled(true);
                userRepository.save(user);
                tokenRepository.delete(vt); // Token artık gereksiz
                return true;
            }
        }
        return false;
    }
}
