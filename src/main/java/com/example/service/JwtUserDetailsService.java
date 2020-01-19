package com.example.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.model.UserDTO;
import com.example.users.UserModel;
import com.example.users.UserRepository;


@Component
public class JwtUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserModel user =  repo.findByUsername(username);
		
		System.out.println("________in loadbyusername ______ " + user);
		
		
		if(user==null) throw new UsernameNotFoundException("User 404");
		
		//return new UserPrincipal(user);
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				new ArrayList<>());
		
	}
	
	public UserRepository save(UserDTO user) {
		
		UserModel newUser = new UserModel();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		return (UserRepository) repo.save(newUser);
		
		
	}

}
