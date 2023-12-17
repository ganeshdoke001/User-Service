package com.lcwd.user.service.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.lcwd.user.service.entity.Hotel;
import com.lcwd.user.service.entity.Rating;
import com.lcwd.user.service.entity.User;
import com.lcwd.user.service.exception.ResourceNotFoundException;
import com.lcwd.user.service.foignclients.HotelService;
import com.lcwd.user.service.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired(required=true)
	private HotelService hotelService;

	private Logger looger = org.slf4j.LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public User saveUser(User user) {
		String randomUserId = UUID.randomUUID().toString();
		user.setId(randomUserId);
		return this.userRepo.save(user);
	}

	@Override
	public User getUser(String id) {
		User user = userRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Resource not found with this id : " + id));
		
		Rating[] ratingOfUser = this.restTemplate.getForObject("http://RATING-SERVICE/api/ratings/user/" + id,
				Rating[].class);
		
		List<Rating>ratingListobj=Arrays.stream(ratingOfUser).toList();
		
		this.looger.info("{}", ratingOfUser);
		List<Rating> ratingList = ratingListobj.stream().map((rating) -> {
			
//			ResponseEntity<Hotel> forEntity = this.restTemplate
//					.getForEntity("http://HOTEL-SERVICE/api/hotels/" + rating.getHotelId(), Hotel.class);
//			Hotel hotel = forEntity.getBody();
			Hotel hotel=this.hotelService.getHotel(rating.getHotelId());
			
			this.looger.info("Response status  : {}", hotel);
			rating.setHotel(hotel);
			return rating;
		}).collect(Collectors.toList());
		user.setRatings(ratingList);
		return user;
	}

	@Override
	public List<User> getAllUsers() {

		return this.userRepo.findAll();
	}

	@Override
	public User updateUser(User user) {
		return this.userRepo.save(user);
	}

	@Override
	public String delete(String id) {
		this.userRepo.deleteById(id);
		return "User Deleted";
	}

}
