package com.lcwd.user.service.service;

import java.util.List;

import com.lcwd.user.service.entity.User;

public interface UserService {

	User saveUser(User user);
	User getUser(String id);
	List<User> getAllUsers();
	User updateUser(User user);
	String delete(String id);
	
	
}
