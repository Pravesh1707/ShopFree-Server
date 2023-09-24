package com.example.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Config.JwtProvider;
import com.example.Exception.UserException;
import com.example.Models.Cart;
import com.example.Models.Users;
import com.example.Repository.UserRepository;
import com.example.Request.LoginRequest;
import com.example.Response.AuthResponse;
import com.example.Service.CartService;
import com.example.Service.CustomeUserServicelmplementation;

@RestController
@RequestMapping("/auth")
public class AuthController {
	private UserRepository userRepository;
	private JwtProvider jwtProvider;
	private PasswordEncoder passwordEncoder;
	private CustomeUserServicelmplementation customUserService;
	private CartService cartService;
	
	public AuthController(UserRepository userRepository,CartService cartService, JwtProvider jwtProvider, CustomeUserServicelmplementation customUserService, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.customUserService = customUserService;
		this.jwtProvider =jwtProvider;
		this.cartService = cartService;
	}
	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse>createUserHandler(@RequestBody Users user)throws UserException{
		
		String email = user.getEmail();
		String pass = user.getPassword();
		String firstName = user.getFirstName();
		String lastName = user.getLastName();
		
		
		Users isEmailExist=userRepository.findByEmail(email);

		if(isEmailExist!=null) {
			throw new UserException("Email is Already Used With Another Account");
		}
		
		Users createdUser=new Users(); 
		createdUser.setEmail(email); 
		createdUser.setPassword(passwordEncoder.encode(pass)); 
		createdUser.setFirstName(firstName);
		createdUser.setLastName(lastName);
		
		Users savedUser=userRepository.save(createdUser);
		Cart cart = cartService.createCart(savedUser);
		
		Authentication authentication=new UsernamePasswordAuthenticationToken(savedUser.getEmail(),savedUser.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtProvider.generateToken(authentication);
		AuthResponse authResponse=new AuthResponse();
		authResponse.setJwt(token);
		authResponse.setMsg("Signup Success");
		return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.CREATED);
	}

	@PostMapping("/signin")
	public ResponseEntity<AuthResponse>loginUserHandler(@RequestBody LoginRequest loginRequest){
		
		String email = loginRequest.getEmail();
		String password = loginRequest.getPass();
		
		Authentication authentication=authenticate(email, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token = jwtProvider.generateToken(authentication);
		
		AuthResponse authResponse=new AuthResponse();
		authResponse.setJwt(token);
		authResponse.setMsg("SignIn Success");
		return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.CREATED);
	}

	private Authentication authenticate(String email, String pass) {
		
		UserDetails userDetails = customUserService.loadUserByUsername(email);
		
		if(userDetails == null) {
			throw new BadCredentialsException("Invalid Username");
		}
		
		if(!passwordEncoder.matches(pass, userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid Password...");
		}
		
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}
}

