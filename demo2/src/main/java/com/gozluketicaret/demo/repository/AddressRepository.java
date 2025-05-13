package com.gozluketicaret.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gozluketicaret.demo.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
	List<Address> findByUserId(Long userId);
}
