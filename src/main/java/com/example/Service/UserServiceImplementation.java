package com.example.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.Config.JwtProvider;
import com.example.Exception.UserException;
import com.example.Models.Users;
import com.example.Repository.UserRepository;

@Service
public class UserServiceImplementation implements UserService{
	
	private UserRepository userRepository;
	private JwtProvider jwtProvider;
	
	public UserServiceImplementation(UserRepository userRepository, JwtProvider jwtProvider) {
		this.jwtProvider=jwtProvider;
		this.userRepository= userRepository;
	}
	
	@Override
	public Users findUserById(Long userId) throws UserException {
		Optional<Users>user=userRepository.findById(userId);
		if(user.isPresent()) {
		return user.get();
		}
		throw new UserException("user not found with id : " + userId);
		
	}
	@Override
	public Users findUserProfileByJwt(String jwt) throws UserException {
		
		String email=jwtProvider.getEmailFromToken(jwt);
		Users user=userRepository.findByEmail(email);
		
		if(user==null) {
			throw new UserException("user not found with email "+email);
		}
		
		return user;
	}

	@Override
	public List<Users> getAlllUsers() {
		List<Users> users = userRepository.getAllUsers();
		return users;
	}
}
