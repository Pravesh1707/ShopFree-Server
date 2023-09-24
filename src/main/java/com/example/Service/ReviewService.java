package com.example.Service;

import java.util.List;

import com.example.Exception.ProductException;
import com.example.Models.Riview;
import com.example.Models.Users;
import com.example.Request.ReviewRequest;

public interface ReviewService {

	public Riview createReive(ReviewRequest req, Users user)throws ProductException;
	public List<Riview> getAllReview(Long productId);
}
