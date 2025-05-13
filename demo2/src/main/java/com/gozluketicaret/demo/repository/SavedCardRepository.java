package com.gozluketicaret.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gozluketicaret.demo.SavedCard;

public interface SavedCardRepository extends JpaRepository<SavedCard, Long> {
    List<SavedCard> findByUserId(Long userId);
}
