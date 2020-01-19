package com.example.users;


import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {
	
	
	@Autowired
	UserRepository userRepo;
	
	@PostMapping(path = "sign-up")
	public UserModel save(@RequestBody UserModel usermodel) {
		
//		BCryptPasswordEncoder password = new BCryptPasswordEncoder();
//		
//		String pass = password.encode(usermodel.getPassword());
		
		usermodel.setPassword(new BCryptPasswordEncoder().encode(usermodel.getPassword()));
		
		UserModel um = userRepo.save(usermodel);
		
		if(um == null) {
			throw new UserNotFoundException("Not Saved");
		}
		
		return um;
	}
	
	@GetMapping("getAllUsers")
	public List<UserModel> getAllUsers() {
		return userRepo.findAll();
	}
	
	@RequestMapping("single-user")
	public Resource<UserModel> getSingleuser(@RequestParam String username) {
		
		UserModel userModel = userRepo.findById(username).orElse(null);
		
		if(userModel == null) {
		
			throw new UserNotFoundException("username -" + username);
		}
		
		Resource<UserModel> resource = new Resource<UserModel>(userModel);
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAllUsers());
		
		resource.add(linkTo.withRel("all-users"));
		
		return resource;
	}
	
	@RequestMapping("logout")
	public String logout(HttpServletRequest request,HttpServletResponse response) {
		
		
		System.out.println("_________ Headers  _______ " + request.getHeader("Authorization"));
		
		return "successfully logout";
	}
	
	@PutMapping(path ="/update/{username}")
	public UserModel updateUser(@PathVariable("username") String username, @RequestBody UserModel usermodelUpdate) throws Exception {
		
		UserModel usermodel = userRepo.findById(username).orElse(null);
		
		
		if(usermodel == null) {
			throw new UserNotFoundException("username -" + username);
		}
		
		if(usermodelUpdate.getUsername() != null) {
			throw new Exception("Message - Can't be updated once you set username!!");
		}
		
		
		if(usermodelUpdate.getAge() != null) {
			usermodelUpdate.setAge(usermodelUpdate.getAge());
		}
		else
		{
			usermodelUpdate.setAge(usermodel.getAge());
		}
		
	    if(usermodelUpdate.getEmail() != null) {
			usermodelUpdate.setEmail(usermodelUpdate.getEmail());
		}
	    else
	    {
	    	usermodelUpdate.setEmail(usermodel.getEmail());
	    }
	    
		if(usermodelUpdate.getName() != null) {
			usermodelUpdate.setName(usermodelUpdate.getName());
		}
		else
		{
			usermodelUpdate.setName(usermodel.getName());
		}
		
		if(usermodelUpdate.getPassword() != null) {
			usermodelUpdate.setPassword(usermodelUpdate.getPassword());
		}
		else
		{
			usermodelUpdate.setPassword(usermodel.getPassword());
		}
		
		System.out.println("Updated Data " + usermodelUpdate);
		
		userRepo.save(usermodelUpdate);
		return usermodelUpdate;
		
	}
	
	@DeleteMapping("delete")
	public UserModel deleteUser(@RequestParam String username) {
		
		UserModel usermodel = userRepo.findById(username).orElse(null);
		
		if(usermodel == null) {
			throw new UserNotFoundException("username -" + username);
		}
		
		userRepo.deleteById(username);
		
		return usermodel;
	}
}

















	