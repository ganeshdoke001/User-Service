package com.lcwd.user.service.api;

import java.util.List;

import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.slf4j.Logger;
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

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/api/users")
public class UserRest {
	
	@Autowired
	private UserService userService;
	
	private Logger looger = org.slf4j.LoggerFactory.getLogger(UserRest.class);
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

	int count=1;
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	//@CircuitBreaker(name = "ratingHotelBreaker",fallbackMethod = "ratingHotelFallback")
	//@Retry(name = "ratingHotelRetry",fallbackMethod = "ratingHotelFallback")
	@RateLimiter(name = "userRateLimiter",fallbackMethod = "ratingHotelFallback")
	public  ResponseEntity<User> getUser(@PathVariable ("id") String id) {
		User user1=  this.userService.getUser(id);
		this.looger.info("the user is  :{}",user1 );
		this.looger.info("retry count :{}",count);
		count++;
		return new ResponseEntity<User>(user1,HttpStatus.OK);
	}
	
	public ResponseEntity<User> ratingHotelFallback(String userId,Exception ex){
		
		this.looger.info("Fallback excuted because some service is down",ex.getMessage());
		User user= new User();
		user.setMail("dummy@gmail.com");
		user.setName("Dummy");
		user.setAbout("This user is created becase some service is down");
		user.setId("1323233");

		return new ResponseEntity<User>(user,HttpStatus.OK);
		
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
