package com.example.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Exception.ProductException;
import com.example.Models.Product;
import com.example.Models.Riview;
import com.example.Models.Users;
import com.example.Repository.ProductRepository;
import com.example.Repository.ReviewRepository;
import com.example.Request.ReviewRequest;

@Service
public class ReviewServiceImplementation implements ReviewService{

	private ReviewRepository reviewRepository;
	private ProductService productService;
	private ProductRepository productRepository;
	
	public ReviewServiceImplementation(ReviewRepository reviewRepository, ProductService productService, ProductRepository productRepository) {
		this.productRepository = productRepository;
		this.reviewRepository = reviewRepository;
		this.productService = productService;
	}
	@Override
	public Riview createReive(ReviewRequest req, Users user) throws ProductException {
		Product product=productService.findProductById(req.getProductId()); 
		Riview review=new Riview();
		review.setUser(user); review.setProduct(product);
		review.setReview(req.getReview());
		review.setCreatedAt(LocalDateTime.now());
		return reviewRepository.save(review);
	}

	@Override
	public List<Riview> getAllReview(Long productId) {
		
		return reviewRepository.getAllProductsReview(productId);
	}

}
