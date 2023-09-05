package com.lcwd.user.service.externalService;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.lcwd.user.service.entities.Rating;

@Service
@FeignClient("RATING-SERVICE")
public interface RatingService {
	
	@PostMapping("/ratings")
	public Rating createRating(Rating values);
	
	@PutMapping("/ratings/{ratingID}")
	public Rating updateRating(@PathVariable String ratingID, Rating rating);

	@DeleteMapping("/ratings/{ratingID}")
	public Rating deleteRating(@PathVariable String ratingID);

}
