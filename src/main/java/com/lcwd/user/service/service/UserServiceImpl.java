package com.lcwd.user.service.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcwd.user.service.entity.User;
import com.lcwd.user.service.exception.ResourceNotFoundException;
import com.lcwd.user.service.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Override
	public User saveUser(User user) {
	String randomUserId=	UUID.randomUUID().toString();
	user.setId(randomUserId);
		return this.userRepo.save(user);
	}

	@Override
	public User getUser(String id) {
			return userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Resource not found with this id : "+id) );
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
