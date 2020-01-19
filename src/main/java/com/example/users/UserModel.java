package com.example.users;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;


import com.example.tasks.TaskModel;

import io.swagger.annotations.ApiModel;


@ApiModel(description="All details about the User Api")
@Entity
public class UserModel 
{
	
	private String name;
	
	private String password;
	
	@Id
	@Size(min=2, message="UserName should have atleast 2 characters")
	private String username;
	
	
	private String email;
	private Integer age;
	
	@OneToMany(mappedBy = "usermodel", cascade = CascadeType.REMOVE)
	private List<TaskModel> taskmodel;
	
	public List<TaskModel> getTaskmodel() {
		return taskmodel;
	}
	public void setTaskmodel(List<TaskModel> taskmodel) {
		this.taskmodel = taskmodel;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Override
	public String toString() {
		return "UserModel [name=" + name + ", password=" + password + ", username=" + username + ", email=" + email
				+ ", age=" + age + "]";
	}
	
	
	
}
