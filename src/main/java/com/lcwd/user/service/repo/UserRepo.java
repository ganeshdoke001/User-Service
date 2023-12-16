package com.lcwd.user.service.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lcwd.user.service.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, String> {

}
