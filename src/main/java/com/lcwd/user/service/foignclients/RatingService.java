package com.lcwd.user.service.foignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.lcwd.user.service.entity.Rating;

@FeignClient(name = "RATING-SERVICE")
public interface RatingService {

	@PostMapping("/api/ratings")
	public Rating create(@RequestBody Rating rating);
}
