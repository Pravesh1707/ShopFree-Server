package com.example.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.Models.Rating;
import com.example.Models.Riview;

public interface ReviewRepository extends JpaRepository<Riview, Long>{

	@Query("SELECT r From Riview r Where r.product.id=:productId")
	public List<Riview> getAllProductsReview(@Param("productId")Long productId);
	
}
