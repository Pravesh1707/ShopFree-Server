package com.example.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Models.Product;
import com.example.Models.Users;
import com.example.Exception.UserException;
import com.example.Service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService=userService;
	}
	
	@GetMapping("/profile")
	public ResponseEntity<Users> getUserProfileHandler(@RequestHeader("Authorization") String jwt) throws UserException{

//		System.out.println("/api/users/profile");
		Users user=userService.findUserProfileByJwt(jwt);
		return new ResponseEntity<Users>(user,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<Users>> getAllUsers(){
		List<Users> products=userService.getAlllUsers();
		return new ResponseEntity<List<Users>>(products,HttpStatus.ACCEPTED);
		
	}
}
