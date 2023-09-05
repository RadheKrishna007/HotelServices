package com.lcwd.hotel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lcwd.hotel.entities.Hotel;
import com.lcwd.hotel.services.HotelService;

@RestController
@RequestMapping("/hotels")
public class HotelController {
	
	@Autowired
	private HotelService hotelService;
	
	@PostMapping
	public ResponseEntity<Hotel> createUser(@RequestBody Hotel hotel){
		Hotel hotel1 = hotelService.create(hotel);
		return ResponseEntity.status(HttpStatus.CREATED).body(hotel1);
	}
	
	@GetMapping("/{hotelId}")
	public ResponseEntity<Hotel> getSingleUser(@PathVariable String hotelId){
		Hotel user1 = hotelService.get(hotelId);
		return ResponseEntity.ok(user1);
	}
	
	@GetMapping
	public ResponseEntity<List<Hotel>> getAllUser(){
		List<Hotel> user1 = hotelService.getAll();
		return ResponseEntity.ok(user1);
	}
	

}
