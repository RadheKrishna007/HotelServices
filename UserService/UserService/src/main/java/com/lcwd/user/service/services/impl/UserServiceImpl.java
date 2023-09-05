package com.lcwd.user.service.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lcwd.user.service.entities.Hotel;
import com.lcwd.user.service.entities.Rating;
import com.lcwd.user.service.entities.User;
import com.lcwd.user.service.exception.ResourceNotFoundException;
import com.lcwd.user.service.externalService.HotelService;
import com.lcwd.user.service.repositories.UserRepository;
import com.lcwd.user.service.services.UserServices;

@Service
public class UserServiceImpl implements UserServices {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private HotelService hotelService;
	
	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public User saveUser(User user) {
		String randomId = UUID.randomUUID().toString();
		user.setUserId(randomId);
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUser() {
		// TODO Auto-generated method stub
		List<User> user = userRepository.findAll();
		user.stream().map(entry ->  commonMethodForApiCall(entry)).collect(Collectors.toList());
		return user;
	}

	@Override
	public User getUser(String userId) {
		// TODO Auto-generated method stub
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found for given ID "+userId));
		
//		ArrayList<Rating> list = restTemplate.getForObject("http://localhost:8083/ratings/user/"+user.getUserId(), ArrayList.class);
//		ObjectMapper mapper = new ObjectMapper();
//		List<Rating> ratingLists = mapper.convertValue(list,new TypeReference<List<Rating>>() { });
//		
//		List<Rating> ratingList = ratingLists.stream().map(i -> {
//			
//			
//			
//			ResponseEntity<Hotel> listWithHotel = restTemplate.getForEntity("http://localhost:8082/hotels/"+i.getHotelId(), Hotel.class);
//			Hotel hotel = listWithHotel.getBody();
//			i.setHotel(hotel);
//
//			logger.info("entry is {}",i);
//			return i;
//		}
//		).collect(Collectors.toList());
//		user.setRatings(commonMethodForApiCall(user));
		
		commonMethodForApiCall(user);
	
		return user; 
	}
	
	public List<Rating> commonMethodForApiCall(User user) {
		
		ArrayList<Rating> list = restTemplate.getForObject("http://RATING-SERVICE/ratings/user/"+user.getUserId(), ArrayList.class);
		ObjectMapper mapper = new ObjectMapper();
		List<Rating> ratingLists = mapper.convertValue(list,new TypeReference<List<Rating>>() { });
		
		List<Rating> ratingList = ratingLists.stream().map(i -> {
	//  Rest Call	
	//		ResponseEntity<Hotel> listWithHotel = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+i.getHotelId(), Hotel.class);
	// Feign Client Call	
			Hotel hotel = hotelService.getHotel(i.getHotelId());
			i.setHotel(hotel);

			logger.info("entry is {}",i);
			return i;
		}
		).collect(Collectors.toList());
		
		logger.info("{}",list);
		
		user.setRatings(ratingList);
	
	return ratingList;
	}
}
