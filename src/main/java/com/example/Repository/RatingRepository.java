package com.example.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.Models.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long>{

	@Query("SELECT r From Rating r Where r.product.id=:productId")
	public List<Rating> getAllProductsRating(@Param("productId")Long productId);
}
