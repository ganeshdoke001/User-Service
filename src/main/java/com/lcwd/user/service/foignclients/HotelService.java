package com.lcwd.user.service.foignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.lcwd.user.service.entity.Hotel;

@FeignClient(name = "HOTEL-SERVICE")
public interface HotelService {

	@GetMapping("/api/hotels/{hotelId}")
	public Hotel getHotel(@PathVariable ("hotelId" )String hotelId);
}
