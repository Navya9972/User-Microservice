package com.connected.car.user.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.connected.car.user.constants.ResponseMessage;
import com.connected.car.user.dto.UserDto;
import com.connected.car.user.entity.User;
import com.connected.car.user.repository.UserRepository;
import com.connected.car.user.service.UserService;
import com.connected.car.user.exceptions.custom.DuplicationException;
import com.connected.car.user.exceptions.custom.ResourceNotFoundException;
import com.connected.car.user.exceptions.custom.UserActivationException;
import com.connected.car.user.exceptions.custom.UserAlreadyActiveException;
import com.connected.car.user.exceptions.custom.UserAlreadyInactiveException;
import com.connected.car.user.exceptions.custom.UserCreationException;
import com.connected.car.user.exceptions.custom.UserDeactivationException;
import com.connected.car.user.exceptions.custom.UserDeletionException;
import com.connected.car.user.exceptions.custom.UserFetchException;
import com.connected.car.user.exceptions.custom.UserNotFoundException;
import com.connected.car.user.exceptions.custom.UserUpdateException;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	private final UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	@Override
	public UserDto createUser(UserDto userDto) {
	    logger.info("Creating user");
	    userDto.setStatus(true);
	    User user = dtoToEntity(userDto);
	    try {
	    	if (userRepository.existsByEmail(userDto.getEmail())) {
	            throw new DuplicationException("Email already exists");
	        }
	    	if (userRepository.existsByPhoneNumber(userDto.getPhoneNumber())) {
	            throw new DuplicationException("Phone number already exists");
	        }
	        User savedUser = userRepository.save(user);
	        UserDto savedUserDto = entityToDto(savedUser);
	        logger.info("User created successfully");
	        return savedUserDto;
	        
	    }catch (DuplicationException e) {
	        logger.error("Exception while creating user");
	        throw new DuplicationException();
	    }
	    
	    catch (Exception e) {
	        logger.error("Exception while creating user");
	        throw new UserCreationException("Failed to create user");
	    }
	}
	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		logger.info("Updating user with ID {}", userId);
		try {
			User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
			user.setFirstName(userDto.getFirstName());
			user.setLastName(userDto.getLastName());
			user.setPhoneNumber(userDto.getPhoneNumber());
			user.setAddress(userDto.getAddress());
			user.setEmail(userDto.getEmail());
			user.setPassword(userDto.getPassword());
			User updatedUser = userRepository.save(user);
			UserDto updatedUserDto = entityToDto(updatedUser);
			logger.info("User updated successfully");
			return updatedUserDto;	
		} catch (UserNotFoundException e) {
			logger.error("User not found for update with ID {}", userId);
			throw new UserNotFoundException(userId);
		} catch (Exception e) {
			logger.error("Exception while updating user with ID {}", userId);
			throw new UserUpdateException(userId);
		}
	}

	@Override
	public UserDto getUserById(Integer userId) {
		logger.info("Fetching user by ID: {}", userId);
		try {
			User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
			UserDto userDto = entityToDto(user);
			logger.info("User fetched successfully: {}", userDto);
			return userDto;
		} catch (UserNotFoundException e) {
			logger.error("User not found with ID {}", userId);
			throw e;
			
		} catch (Exception e) {
			logger.error("Exception while fetching user with ID {}", userId);
			throw new UserFetchException(userId);
		}
	}

	
	@Override
	public List<UserDto> getAllUsers() {
		logger.info("Fetching all users");
		try {
			List<User> users = userRepository.findAll();
			List<UserDto> userDtos = users.stream().map(this::entityToDto).toList();
			if (userDtos.isEmpty()) {
				throw new ResourceNotFoundException(ResponseMessage.NOT_FOUND);
			}
			logger.info("Fetched {} users successfully", userDtos.size());
			return userDtos;	
		} catch (ResourceNotFoundException e) {
			logger.error(ResponseMessage.NOT_FOUND);
			throw e;	
		} catch (Exception e) {
			logger.error("Exception while fetching all users");
			throw new UserFetchException("Failed to fetch all users");
		}
		
	}
	
	@Override
	public List<UserDto> getAllActiveUsers() {
		logger.info("Fetching All Active users");
		try {
			List<User> users = userRepository.findByStatus(true);
			List<UserDto> userDtos = users.stream().map(this::entityToDto).toList();
			if (userDtos.isEmpty()) {
				throw new ResourceNotFoundException(ResponseMessage.NOT_FOUND);
			}
			logger.info("Fetched {} Active users successfully", userDtos.size());
			return userDtos;	
		} catch (ResourceNotFoundException e) {
			logger.error(ResponseMessage.NOT_FOUND);
			throw e;	
		} catch (Exception e) {
			logger.error("Exception while fetching all Active users");
			throw new UserFetchException("Failed to fetch all Active users");
		}
		
	}
	
	
	@Override
	public void deleteUser(Integer userId) {
		logger.info("Deleting user with ID: {}", userId);
		try {
			Optional<User> optionalUser = userRepository.findById(userId);

			if (optionalUser.isPresent()) {
				User user = optionalUser.get();
				userRepository.delete(user);
				logger.info("User deleted successfully");
			} else {
				throw new UserNotFoundException(userId);
			}
		} catch (UserNotFoundException e) {
			// This exception is thrown when trying to delete a non-existent user.
			logger.error("User not found for deletion with ID {}", userId);
			throw new UserNotFoundException(userId);
		} catch (Exception e) {
			logger.error("Exception while deleting user with ID {}", userId);
			throw new UserDeletionException(userId);
		}
	}

	@Override
	public boolean getStatusById(Integer userId) {
		logger.info("Fetching status for user with ID: {}", userId);
		try {
			User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
			boolean status = user.isStatus();
			logger.info("Status for user with ID {}: {}", userId, status);
			return status;
		} catch (UserNotFoundException e) {
			logger.error("User not found with ID {}", userId);
			throw new UserNotFoundException(userId);
		} catch (Exception e) {
			logger.error("Exception while fetching status for user with ID {}", userId);
			throw new UserFetchException(userId);
		}
	}
	@Override
	public void userDeactivation(Integer userId) {
	    logger.info("Deactivating user with ID: {}", userId);
	    try {
	        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
	        if (user.isStatus()) {
	            user.setStatus(false);
	            userRepository.save(user);
	            logger.info("User deactivated successfully");
	        } else {
	            throw new UserAlreadyInactiveException(userId);
	        }
	    } catch (UserNotFoundException e) {
	        logger.error("User not found for deactivation with ID {}", userId);
	        throw e;
	        
	    } catch (UserAlreadyInactiveException e) {
	        logger.warn("User with ID {} is already inactive", userId);
	        throw e;
	    } catch (Exception e) {
	        logger.error("Exception while deactivating user with ID {}", userId);
	        throw new UserDeactivationException(userId);
	    }
	}


	@Override
	public void userActivation(Integer userId) {
	    logger.info("Activating user with ID: {}", userId);
	    try {
	        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

	        if (!user.isStatus()) {
	            user.setStatus(true);
	            userRepository.save(user);
	            logger.info("User activated successfully");
	        } else {
	            throw new UserAlreadyActiveException(userId);
	        }
	    } catch (UserNotFoundException e) {
	        logger.error("User not found for activation with ID {}", userId);
	        throw e;
	    } catch (UserAlreadyActiveException e) {
	        logger.warn("User with ID {} is already active", userId);
	        throw e;
	    } catch (Exception e) {
	        logger.error("Exception while activating user with ID {}", userId);
	        throw new UserActivationException(userId);
	    }
	}

	private User dtoToEntity(UserDto userDto) {
		User user = new User();
		user.setId(userDto.getId());
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setEmail(userDto.getEmail());
		user.setPhoneNumber(userDto.getPhoneNumber());
		user.setAddress(userDto.getAddress());
		user.setStatus(userDto.isStatus());
		user.setPassword(userDto.getPassword());
		return user;
	}

	private UserDto entityToDto(User user) {
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setFirstName(user.getFirstName());
		userDto.setLastName(user.getLastName());
		userDto.setEmail(user.getEmail());
		userDto.setPhoneNumber(user.getPhoneNumber());
		userDto.setAddress(user.getAddress());
		userDto.setStatus(user.isStatus());
		userDto.setPassword(user.getPassword());
		return userDto;
	}

}
