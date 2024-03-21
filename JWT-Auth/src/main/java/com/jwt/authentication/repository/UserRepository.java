package com.jwt.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jwt.authentication.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	public User findByUsername(String username);

}
