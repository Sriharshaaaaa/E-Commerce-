package com.zee.service;

import java.util.List;

import com.zee.model.Product;
import com.zee.model.Review;
import com.zee.model.User;
import com.zee.request.CreateReviewRequest;

public interface ReviewService {
	
	Review createReview(
			CreateReviewRequest req,
			User user,
			Product product);
	List<Review> getReviewByProductId(Long productId);
	
	Review updateReview(Long reviewId, String reviewText, double rating, Long userId) throws Exception;
	
	void deleteReview(Long reviewId,Long userId) throws Exception;
	
	Review getReviewById(Long reviewId) throws Exception;
}
