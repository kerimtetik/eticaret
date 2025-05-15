package com.gozluketicaret.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users_account")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Bu, id'nin otomatik artmasını sağlar.
    private Long id;
    
    private String email;
   
	private String password;
    private String firstName;
    private String lastName;
    private String username;   // Yeni eklenen alan
    private String address;    // Yeni eklenen alan
    private String phone;      // Yeni eklenen alan
    private boolean enabled;
    private String role = "USER"; // Varsayılan rol
    

    // Getters and Setters
    public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    

}