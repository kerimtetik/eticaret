package com.gozluketicaret.demo.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	 @Autowired
	    private JavaMailSender mailSender;

	    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

	    public void send(String to, String subject, String body) {
	        logger.info("E-posta gönderiliyor: {}", to);
	        logger.debug("Konu: {}, İçerik: {}", subject, body);

	        try {
	            MimeMessage message = mailSender.createMimeMessage();
	            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

	            helper.setTo(to);
	            helper.setSubject(subject);
	            helper.setText(body, true); 

	            mailSender.send(message);
	            logger.info("E-posta başarıyla gönderildi: {}", to);
	        } catch (MessagingException e) {
	            logger.error("E-posta gönderimi başarısız: {}", e.getMessage(), e);
	            throw new RuntimeException("E-posta gönderme hatası: " + e.getMessage(), e);
	        }
	    }
}
 

