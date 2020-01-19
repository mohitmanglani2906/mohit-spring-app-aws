package com.example.tasks;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.users.UserModel;
import com.example.users.UserNotFoundException;
import com.example.users.UserRepository;

@RestController
@RequestMapping("tasks")
public class TaskController {
	
	@Autowired
	TaskRepository taskRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@PostMapping("addtasks/{username}/save-tasks")
	public TaskModel saveTask(@PathVariable String username ,@RequestBody TaskModel taskmodel) {

	
        Optional<UserModel> userOptional = userRepo.findById(username);
		

		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("username -" + username);
		}
		
		UserModel usermodel = userOptional.get();
		taskmodel.setUsermodel(usermodel);
		
		taskRepo.save(taskmodel);
		
		
		return taskmodel;
	}
	
	
	@GetMapping("getTasks/{username}/your-tasks")
	public List<TaskModel> getTasks(@PathVariable String username) {
		
		UserModel usermodel = userRepo.findById(username).orElse(null);
		
		if(usermodel == null) {
			throw new UserNotFoundException("username -" + username); 
		}
		
		List<TaskModel> taskLists = usermodel.getTaskmodel();
		
		if(taskLists.isEmpty()) {
			throw new UserNotFoundException("Task Not Found For " + username); 
		}
		
		return taskLists;
		
		
	}
	
	@DeleteMapping("deleteTask/{username}/{id}/delete-task")
    public TaskModel deleteTaskById(@PathVariable String username, @PathVariable Integer id) {

        UserModel usermodel = userRepo.findById(username).orElse(null);
        
		if(usermodel == null) {
			throw new UserNotFoundException("username -" + username); 
		}
		
		TaskModel taskmodel  = taskRepo.findById(id).orElse(null);
		
		if(taskmodel == null) {
			throw new UserNotFoundException("Task Not Found For -" + username); 
		}
		
		taskRepo.deleteById(id);
		
		return taskmodel;
    }
	
	@PutMapping("updateTask/{username}/{id}/update-task")
	public TaskModel updateTaskByid(@PathVariable String username, @PathVariable Integer id,@RequestBody TaskModel taskmodelupdate) {
		
		
        UserModel usermodel = userRepo.findById(username).orElse(null);
        TaskModel taskmodel  = taskRepo.findById(id).orElse(null);
        
		if(usermodel == null) {
			throw new UserNotFoundException("username -" + username); 
		}
		
		if(taskmodel == null) {
			throw new UserNotFoundException("Task Not Found For -" + username); 
		}
		
		if(taskmodelupdate.getDescription() != null) {
			taskmodelupdate.setDescription(taskmodelupdate.getDescription());
		}
		else
		{
			taskmodelupdate.setDescription(taskmodel.getDescription());
		}
		
		taskmodelupdate.setId((taskmodel.getId()));
		taskmodelupdate.setUsermodel(usermodel);
			
		taskRepo.save(taskmodelupdate);
		
		
		return taskmodelupdate;
		
	}
	
	
}






