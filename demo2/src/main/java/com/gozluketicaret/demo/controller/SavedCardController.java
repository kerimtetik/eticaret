package com.gozluketicaret.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gozluketicaret.demo.SavedCard;
import com.gozluketicaret.demo.repository.SavedCardRepository;

@RestController
@RequestMapping("/api/cards")
public class SavedCardController {

    @Autowired
    private SavedCardRepository savedCardRepository;

    @PostMapping("/save")
    public ResponseEntity<?> saveCard(@RequestBody Map<String, String> body) {
        Long userId = Long.valueOf(body.get("userId"));
        String cardHolder = body.get("cardHolderName");
        String cardNumber = body.get("cardNumber");
        String last4 = cardNumber.substring(cardNumber.length() - 4);
        String expiry = body.get("expiry").trim();
        String[] parts = expiry.split("/");

        SavedCard card = new SavedCard();
        card.setUserId(userId);
        card.setCardHolderName(cardHolder);
        card.setCardNumberLast4(last4);
        card.setExpiryMonth(parts[0]);
        card.setExpiryYear(parts[1]);

        return ResponseEntity.ok(savedCardRepository.save(card));
    }

    @GetMapping("/{userId}")
    public List<SavedCard> getSavedCards(@PathVariable Long userId) {
        return savedCardRepository.findByUserId(userId);
    }
}

