package com.example.Service;

import java.util.List;

import com.example.Exception.ProductException;
import com.example.Models.Rating;
import com.example.Models.Users;
import com.example.Request.RatingRequest;

public interface RatingService {

	public Rating createRating(RatingRequest req, Users user) throws ProductException;
	public List<Rating> getProductsRating(Long productId);
}
