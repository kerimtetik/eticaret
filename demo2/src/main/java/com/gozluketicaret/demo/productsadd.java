package com.gozluketicaret.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gozluketicaret.demo.repository.ProductRepository;

@Configuration
public class productsadd {

    @Bean
    public CommandLineRunner loadData(ProductRepository productRepository) {
        return args -> {
        	 // Product p1=new Product( "Rayban", "Gu216", true, "dafsdfs", 1234, "dfsd", "/images/raybangu021637.jpg");
        	 // productRepository.save(p1);
           
        };
    }
  
}



