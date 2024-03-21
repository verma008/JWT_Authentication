package com.jwt.authentication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jwt.authentication.entities.User;
import com.jwt.authentication.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		// fetch user from database and load them
		User user=this.userRepository.findByUsername(username);
		if(user==null)
		{
			throw new UsernameNotFoundException("user doesn't exist in database");
		}
		
		CustomUserDetails customeUserDetails=new CustomUserDetails(user);
		
		return customeUserDetails;
	}

}
