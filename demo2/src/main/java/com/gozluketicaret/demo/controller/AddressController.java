package com.gozluketicaret.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gozluketicaret.demo.Address;
import com.gozluketicaret.demo.repository.AddressRepository;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    private AddressRepository addressRepository;

    @GetMapping("/{userId}")
    public List<Address> kullaniciAdresleriniGetir(@PathVariable Long userId) {
        return addressRepository.findByUserId(userId);
    }

    @PostMapping("/ekle")
    public Address yeniAdresEkle(@RequestBody Address address) {
        return addressRepository.save(address);
    }
}
