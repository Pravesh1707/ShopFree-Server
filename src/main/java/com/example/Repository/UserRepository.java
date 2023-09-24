package com.example.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.Models.Users;

public interface UserRepository extends JpaRepository<Users, Long>{

	public Users findByEmail(String email);
	
	@Query("SELECT u from Users u")
	public List<Users> getAllUsers();
}
