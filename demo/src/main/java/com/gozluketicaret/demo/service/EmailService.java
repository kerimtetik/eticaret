package com.gozluketicaret.demo.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void send(String to, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true); // true = HTML içeriği desteklenir

            mailSender.send(message);
            System.out.println("E-posta gönderildi: " + to);
        } catch (MessagingException e) {
        	System.err.println("E-posta gönderilemedi: " + e.getMessage());  // log
            throw new RuntimeException("E-posta gönderme hatası: " + e.getMessage(), e);
        }
    }
}

