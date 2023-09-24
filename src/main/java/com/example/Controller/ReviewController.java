package com.example.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Exception.ProductException;
import com.example.Exception.UserException;
import com.example.Models.Riview;
import com.example.Models.Users;
import com.example.Request.ReviewRequest;
import com.example.Service.ReviewService;
import com.example.Service.UserService;



@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
	
	private ReviewService reviewService;
	private UserService userService;
	
	public ReviewController(ReviewService reviewService,UserService userService) {
		this.reviewService=reviewService;
		this.userService=userService;
		// TODO Auto-generated constructor stub
	}
	@PostMapping("/create")
	public ResponseEntity<Riview> createReviewHandler(@RequestBody ReviewRequest req,@RequestHeader("Authorization") String jwt) throws UserException, ProductException{
		Users user=userService.findUserProfileByJwt(jwt);
		System.out.println("product id "+req.getProductId()+" - "+req.getReview());
		Riview review=reviewService.createReive(req, user);
		System.out.println("product review "+req.getReview());
		return new ResponseEntity<Riview>(review,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/product/{productId}")
	public ResponseEntity<List<Riview>> getProductsReviewHandler(@PathVariable Long productId){
		List<Riview>reviews=reviewService.getAllReview(productId);
		return new ResponseEntity<List<Riview>>(reviews,HttpStatus.OK);
	}

}
