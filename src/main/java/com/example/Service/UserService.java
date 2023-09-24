package com.example.Service;

import java.util.List;

import com.example.Exception.UserException;
import com.example.Models.Users;

public interface UserService {

	public Users findUserById(Long userId) throws UserException;
	
	public Users findUserProfileByJwt(String jwt) throws UserException;
	
	public List<Users> getAlllUsers() ;
}
