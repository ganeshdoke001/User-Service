package com.lcwd.user.service.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lcwd.user.service.entity.User;
import com.lcwd.user.service.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserRest {
	
	@Autowired
	private UserService userService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<User>  saveUser(@RequestBody User user) {
		User user1= this.userService.saveUser(user);
		return new ResponseEntity<User>(user1,HttpStatus.CREATED);
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.ACCEPTED)
	public ResponseEntity<User>  updateUser(@RequestBody User user) {
		User user1= this.userService.updateUser(user);
		return new ResponseEntity<User>(user1,HttpStatus.ACCEPTED);
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public  ResponseEntity<User> getUser(@PathVariable ("id") String id) {
		User user1=  this.userService.getUser(id);
		return new ResponseEntity<User>(user1,HttpStatus.OK);
	}
	
	@GetMapping()
	@ResponseStatus(HttpStatus.OK)
	public List<User>getAll(){
		return this.userService.getAllUsers();
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.CONTINUE)
	public String delete(String id) {
		return this.userService.delete(id);
	}
}
