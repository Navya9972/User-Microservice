package com.connected.car.user.service;

import java.util.List;

import com.connected.car.user.dto.UserDto;

public interface UserService {

	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user, Integer userId);
	UserDto getUserById(Integer userId);
	List<UserDto> getAllUsers();
	void deleteUser(Integer userId);
	boolean getStatusById(Integer userId);
	void userDeactivation(Integer userId);
	void userActivation(Integer userId);
	List<UserDto> getAllActiveUsers();
	
}
