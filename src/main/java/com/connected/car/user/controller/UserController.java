package com.connected.car.user.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.connected.car.user.constants.ResponseMessage;
import com.connected.car.user.dto.UserDto;
import com.connected.car.user.entity.ApiResponse;
import com.connected.car.user.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/api/users")
@Validated
public class UserController {

	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/create")
	public ResponseEntity<ApiResponse> createUser(@Valid @RequestBody UserDto userDto) {
		UserDto user = userService.createUser(userDto);
		ApiResponse response = new ApiResponse(true, ResponseMessage.CREATE_MESSAGE, user);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PutMapping("/update/{userId}")
	public ResponseEntity<ApiResponse> updateUser(@Valid @RequestBody UserDto userDto,
			@PathVariable("userId") Integer userId) {
		UserDto updatedUser = userService.updateUser(userDto, userId);
		ApiResponse response = new ApiResponse(true, ResponseMessage.UPDATE_MESSAGE, updatedUser);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer userId) {
		userService.deleteUser(userId);
		ApiResponse response = new ApiResponse(true, ResponseMessage.DELETE_MESSAGE);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/getAllUsers")
	public ResponseEntity<ApiResponse> getAllUsers() {
		List<UserDto> userDetails = userService.getAllUsers();
		ApiResponse response = new ApiResponse(true, ResponseMessage.FETCHING_MESSAGE, userDetails);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/getAllActiveUsers")
	public ResponseEntity<ApiResponse> getAllActiveUsers() {
		List<UserDto> userDetails = userService.getAllActiveUsers();
		ApiResponse response = new ApiResponse(true, ResponseMessage.FETCHING_MESSAGE, userDetails);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/getUser/{userId}")
	public ResponseEntity<ApiResponse> getUser(@PathVariable("userId") Integer userId) {
		UserDto user = userService.getUserById(userId);
		ApiResponse response = new ApiResponse(true, ResponseMessage.FETCHING_MESSAGE, user);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/getStatus/{userId}")
	public ResponseEntity<ApiResponse> getStatusById(@PathVariable("userId") Integer userId) {
		boolean status = userService.getStatusById(userId);
		if (status) {
			ApiResponse response = new ApiResponse(true, ResponseMessage.ACTIVE_MESSAGE);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			ApiResponse response = new ApiResponse(true, ResponseMessage.INACTIVE_MESSAGE);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	@PutMapping("/InactiveStatus/{userId}")
	public ResponseEntity<ApiResponse> updateStatusToInactive(@PathVariable("userId") Integer userId) {
		userService.userDeactivation(userId);
		ApiResponse response = new ApiResponse(true, ResponseMessage.INACTIVATE_MESSAGE);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/activeStatus/{userId}")
	public ResponseEntity<ApiResponse> updateStatusToActive(@PathVariable("userId") Integer userId) {
		userService.userActivation(userId);
		ApiResponse response = new ApiResponse(true, ResponseMessage.ACTIVATE_MESSAGE);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	

}