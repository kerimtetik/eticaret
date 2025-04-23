package com.gozluketicaret.demo.repository;


import com.gozluketicaret.demo.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	User findByUsernameAndPassword(String username, String password);
}