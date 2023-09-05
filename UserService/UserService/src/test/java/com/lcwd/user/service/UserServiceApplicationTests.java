package com.lcwd.user.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lcwd.user.service.entities.Rating;
import com.lcwd.user.service.externalService.RatingService;

@SpringBootTest
class UserServiceApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Autowired
	RatingService ratingService;
	
//	@Test
//	void createRating() {
//		Rating rating = Rating.builder().rating(10).hotelId("").userId("").build();
//		Rating savedRating = ratingService.createRating(rating);
//		
//		System.out.println("new saved rating are "+ savedRating);
//	}

}
