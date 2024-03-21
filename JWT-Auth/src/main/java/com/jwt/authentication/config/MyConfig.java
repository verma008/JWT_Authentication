package com.jwt.authentication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jwt.authentication.JWTConfig.JwtAuthenticationEntryPoint;
import com.jwt.authentication.JWTConfig.JwtAuthenticationFilter;


@Configuration
public class MyConfig {
	
	@Autowired
    private JwtAuthenticationFilter filter;
	
	@Autowired
	private JwtAuthenticationEntryPoint point;

	
//	@Bean
//	public UserDetailsService userDetailsService() {
//		return new UserDetailsServiceImpl();
//	}
	
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception{
		return builder.getAuthenticationManager();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
	{
		http.csrf(csrf->csrf.disable())
		    .cors(cors->cors.disable())
		    .authorizeHttpRequests(auth->auth.requestMatchers("/create-user").permitAll().requestMatchers("/create-token").permitAll().requestMatchers("/name").permitAll().anyRequest().authenticated())
		    .exceptionHandling(ex -> ex.authenticationEntryPoint(point));
		
		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
}
