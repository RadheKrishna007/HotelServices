package com.lcwd.user.service.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lcwd.user.service.entities.User;
import com.lcwd.user.service.services.UserServices;

import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserServices userService;
	
//	@Autowired
//	private Logger logger = LoggerFactory.getLogger(UserController.class);
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	
	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user){
		User user1 = userService.saveUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(user1);
	}
	
	int retryCount=0;
	
	@GetMapping("/{userId}")
//	@CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod="ratingHotelFallback")
	@Retry(name = "ratingHotelService", fallbackMethod="ratingHotelFallback")
	public ResponseEntity<User> getSingleUser(@PathVariable String userId){
		logger.info("retry count: "+retryCount);
		retryCount++;
		User user1 = userService.getUser(userId);
		
		return ResponseEntity.ok(user1);
	}
	
	//creating fall back method for circuitbreaker
	// method return type and argument should be same as the the method from where circuit breaker is called
	public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex){
	//	logger.info("Fallback is executed because service is down : ",ex.getMessage());
		User user = User.builder()
				.email("dummy@gmail.com")
				.name("Dummy")
				.about("This dummy is created for testing service down in circuit breaker")
				.userId("1827373").build();
		return new ResponseEntity<>(user,HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<User>> getAllUser(){
		List<User> user1 = userService.getAllUser();
		return ResponseEntity.ok(user1);
	}
	
	@GetMapping("/testing")
	public String testing() {
		return "Hello World";
	}
	
}
