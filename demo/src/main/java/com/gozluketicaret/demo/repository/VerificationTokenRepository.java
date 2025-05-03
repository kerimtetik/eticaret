package com.gozluketicaret.demo.repository;

import com.gozluketicaret.demo.model.VerificationToken;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
	Optional<VerificationToken> findByToken(String token);

}
