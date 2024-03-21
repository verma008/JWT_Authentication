package com.jwt.authentication.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.jwt.authentication.DTO.JwtRequestDTO;
import com.jwt.authentication.DTO.JwtResponse;
import com.jwt.authentication.entities.User;

public interface UserService {
	
	public User createUser(User user) throws Exception;
	
	public ResponseEntity<JwtResponse> createToken(JwtRequestDTO jwtRequest);

	public List<User> allUser();

}
