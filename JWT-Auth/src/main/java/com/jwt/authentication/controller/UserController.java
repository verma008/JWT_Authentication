package com.jwt.authentication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.authentication.DTO.JwtRequestDTO;
import com.jwt.authentication.DTO.JwtResponse;
import com.jwt.authentication.entities.User;
import com.jwt.authentication.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/name")
	public String myName()
	{
		return "Archana";
	}
	
	
	@GetMapping("/allUser")
	public List<User> allUser()
	{
		return userService.allUser();
	}
	
	
	@PostMapping("/create-user")
	public User createUser(@RequestBody User user) throws Exception
	{
		System.out.println("controller");
		return userService.createUser(user);
	}
	
	
	@PostMapping("/create-token")
	public ResponseEntity<JwtResponse> createToken(@RequestBody JwtRequestDTO jwtRequest)
	{
		return userService.createToken(jwtRequest);
	}

//	@ExceptionHandler(BadCredentialsException.class)
//    public String exceptionHandler() {
//        return "Credentials Invalid !!";
//    }
}
