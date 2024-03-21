package com.jwt.authentication.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jwt.authentication.DTO.JwtRequestDTO;
import com.jwt.authentication.DTO.JwtResponse;
import com.jwt.authentication.JWTConfig.JwtUtil;
import com.jwt.authentication.entities.User;
import com.jwt.authentication.repository.UserRepository;
import com.jwt.authentication.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Override
	public User createUser(User user) throws Exception {
		
		User u=this.userRepository.findByUsername(user.getUsername());
		if(u==null)
		{

			
			User u1=new User();
			u1.setUsername(user.getUsername());
			u1.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//			u.setPassword(user.getPassword());
			u1.setRole(user.getRole());
			
     		return userRepository.save(u1);
			
			
			
//			User u2=this.userRepository.save(u1);
//			return u2;
//			return userRepository.save(user);
		}
		else {
			throw new Exception("user is already exist in database");
		}
	}

	
	@Override
	public ResponseEntity<JwtResponse> createToken(JwtRequestDTO jwtRequest) {
		
		this.doAuthenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
		
		
		// find user from database

        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getUsername());
        String token = this.jwtUtil.generateToken(userDetails);
	
        JwtResponse response=JwtResponse.builder()
        		        .jwtToken(token).build();
        
        return new ResponseEntity<>(response, HttpStatus.OK);
	}

	
	private void doAuthenticate(String username, String password)
	{
		UsernamePasswordAuthenticationToken authentication= new UsernamePasswordAuthenticationToken(username, password);
		try {
			manager.authenticate(authentication);
		}catch(BadCredentialsException e)
		{
			throw new BadCredentialsException("Invalid Username and Password !!");
		}
	}

	
	@ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!gghhgg";
    }
	

	@Override
	public List<User> allUser() {
		return userRepository.findAll();
	}

}
