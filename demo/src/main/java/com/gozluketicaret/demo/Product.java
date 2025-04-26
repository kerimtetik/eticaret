package com.gozluketicaret.demo;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Product {
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    private String name;
	    private String product_code;
		private Boolean polarized;
	    private String brand;
	    private double price;
	    private String gender;
	    private String imageUrl;
	    private Integer stock;
	    
	    public Product() {
	        // Hibernate için boş constructor
	    }

	    public Product(String name,String product_code,Boolean polarized,String brand,double price,String gender,String imageUrl, Integer stock ) {
	    	
	    	this.name=name;
	    	this.product_code=product_code;
	    	this.polarized=polarized;
	    	this.brand=brand;
	    	this.price=price;
	    	this.gender=gender;
	    	this.imageUrl=imageUrl;
	    	this.stock=stock;
	    	
	    	
	    }
	    
	    public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getProduct_code() {
			return product_code;
		}
		public void setProduct_code(String product_code) {
			this.product_code = product_code;
		}
		public Boolean getPolarized() {
			return polarized;
		}
		public void setPolarized(Boolean polarized) {
			this.polarized = polarized;
		}
		public String getBrand() {
			return brand;
		}
		public void setBrand(String brand) {
			this.brand = brand;
		}
		public double getPrice() {
			return price;
		}
		public void setPrice(double price) {
			this.price = price;
		}
		public String getGender() {
			return gender;
		}
		public void setGender(String gender) {
			this.gender = gender;
		}
		public String getImageUrl() {
			return imageUrl;
		}
		public void setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
		}
		public Integer getStock() {
			return stock;
		}
		public void setStock(Integer stock) {
			this.stock = stock;
		}

	    
	    
}
