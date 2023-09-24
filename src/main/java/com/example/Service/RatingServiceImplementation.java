package com.example.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Exception.ProductException;
import com.example.Models.Product;
import com.example.Models.Rating;
import com.example.Models.Users;
import com.example.Repository.RatingRepository;
import com.example.Request.RatingRequest;

@Service
public class RatingServiceImplementation implements RatingService{

	
	private RatingRepository ratingRepository;
	private ProductService productService;
	
	public RatingServiceImplementation(RatingRepository ratingRepository, ProductService productService) {
		this.ratingRepository =ratingRepository;
		this.productService = productService;
	}
	
	@Override
	public Rating createRating(RatingRequest req, Users user) throws ProductException {
		Product product=productService.findProductById(req.getProductId());
		
		Rating rating=new Rating();
		rating.setProduct(product);
		rating.setUsers(user);
		rating.setRating(req.getRating());
		rating.setCreatedAt(LocalDateTime.now());
		return ratingRepository.save(rating);
	}

	@Override
	public List<Rating> getProductsRating(Long productId) {
		return ratingRepository.getAllProductsRating(productId);
	}

}
