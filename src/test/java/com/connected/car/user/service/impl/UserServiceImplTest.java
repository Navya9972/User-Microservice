package com.connected.car.user.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.connected.car.user.dto.UserDto;
import com.connected.car.user.entity.Address;
import com.connected.car.user.entity.User;
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
import com.connected.car.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

import ch.qos.logback.classic.Logger;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceImplTest {

	@InjectMocks
	private UserServiceImpl userServiceImpl;
	
	@Mock
	private UserRepository userRepository;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test()
	@Order(1)
	void createUserTest()
	{
		UserDto userDto = getValidUserDto();
        Address address = getValidUserDto().getAddress();
		
        User userEntity = new User();
        userEntity.setId(1);
        userEntity.setFirstName("John");
        userEntity.setEmail("john@gmail.com");
        userEntity.setPassword("abcd");
        
        userEntity.setLastName("Doe");
        userEntity.setPhoneNumber("9085753478");
        
        //Address address1 = new Address();
        getValidUserDto().getAddress().setStreet("shantinagara cross");
        address.setCity("derebail");
        address.setState("Karnataka");
        address.setZipCode("3456788");
        address.setCountry("India");
        userEntity.setAddress(address);

        when(userRepository.save(any(User.class))).thenReturn(userEntity);

        UserDto savedUserDto = userServiceImpl.createUser(userDto);
        verify(userRepository, times(1)).save(any(User.class));
        assertEquals(1, savedUserDto.getId());
        assertEquals("John", savedUserDto.getFirstName());
        assertEquals("john@gmail.com", savedUserDto.getEmail());
        assertEquals("abcd", savedUserDto.getPassword());
        assertEquals("Doe", savedUserDto.getLastName());
        assertEquals("9085753478", savedUserDto.getPhoneNumber());
        assertEquals(address, savedUserDto.getAddress());
           
	}
	
	@Test()
	@Order(2)
	void getUserByIdTest()
	{
        User userEntity = new User();
        userEntity.setId(1);
        userEntity.setFirstName("John");
        userEntity.setEmail("john@gmail.com");
        userEntity.setPassword("abcd");
        userEntity.setLastName("Doe");
        userEntity.setPhoneNumber("9085753478");
        
        Address address = new Address();
        address.setStreet("shantinagara cross");
        address.setCity("derebail");
        address.setState("Karnataka");
        address.setZipCode("3456788");
        address.setCountry("India");
        userEntity.setAddress(address);

        when(userRepository.findById(1)).thenReturn(Optional.of(userEntity));
        UserDto userDto = userServiceImpl.getUserById(1);
        verify(userRepository, times(1)).findById(1);
        assertEquals(1, userDto.getId());
        assertEquals("John", userDto.getFirstName());
        assertEquals("john@gmail.com", userDto.getEmail());
        assertEquals("abcd", userDto.getPassword());
        assertEquals("Doe", userDto.getLastName());
        assertEquals("9085753478", userDto.getPhoneNumber());
        assertEquals(address, userDto.getAddress());
	}
	
	@Test()
	@Order(3)
	void getAllUsersTest()
	{
        User user1 = new User();
        user1.setId(1);
        user1.setFirstName("John");
        user1.setEmail("john@gmail.com");
        user1.setPassword("abcd");
        user1.setLastName("desouza");
        user1.setPhoneNumber("9972850754");
        
        Address address = new Address();
        address.setStreet("shantinagara cross");
        address.setCity("derebail");
        address.setState("Karnataka");
        address.setZipCode("3456788");
        address.setCountry("India");
        user1.setAddress(address);
        

        User user2 = new User();
        user2.setId(2);
        user2.setFirstName("Jane");
        user2.setEmail("jane@gmail.com");
        user2.setPassword("xyz");
        user2.setLastName("Navya");
        user2.setPhoneNumber("9085753478");
        
        Address address1 = new Address();
        address1.setStreet("shantinagara cross");
        address1.setCity("derebail");
        address1.setState("Karnataka");
        address1.setZipCode("3456788");
        address1.setCountry("India");
        user2.setAddress(address1);

        List<User> userEntities = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(userEntities);
        List<UserDto> userDtos = userServiceImpl.getAllUsers();
        verify(userRepository, times(1)).findAll();
        assertEquals(2, userDtos.size());

        UserDto userDto1 = userDtos.get(0);
        assertEquals(1, userDto1.getId());
        assertEquals("John", userDto1.getFirstName());
        assertEquals("john@gmail.com", userDto1.getEmail());
        assertEquals("abcd", userDto1.getPassword());
        assertEquals("desouza", userDto1.getLastName());
        assertEquals("9972850754", userDto1.getPhoneNumber());
        assertEquals(address, userDto1.getAddress());
        
        UserDto userDto2 = userDtos.get(1);
        assertEquals(2, userDto2.getId());
        assertEquals("Jane", userDto2.getFirstName());
        assertEquals("jane@gmail.com", userDto2.getEmail());
        assertEquals("xyz", userDto2.getPassword());
        assertEquals("Navya", userDto2.getLastName());
        assertEquals("9085753478", userDto2.getPhoneNumber());
        assertEquals(address1, userDto2.getAddress());
        
	}
	
	@Test()
	@Order(4)
	void updateUserTest()
	{
        UserDto userDto = new UserDto();
        userDto.setId(1);
        userDto.setFirstName("John Doe");
        userDto.setEmail("johndoe@example.com");
        userDto.setPassword("newpassword");
        userDto.setLastName("Navya");
        userDto.setPhoneNumber("9085753478");
        
        Address address1 = new Address();
        address1.setStreet("shantinagara cross");
        address1.setCity("derebail");
        address1.setState("Karnataka");
        address1.setZipCode("3456788");
        address1.setCountry("India");
        userDto.setAddress(address1);

        User userEntity = new User();
        userEntity.setId(1);
        userEntity.setFirstName("Old Name");
        userEntity.setEmail("oldemail@example.com");
        userEntity.setPassword("oldpassword");
        userEntity.setLastName("Navya");
        userEntity.setPhoneNumber("9085753478");
        
        Address address2 = new Address();
        address2.setStreet("shantinagara cross");
        address2.setCity("derebail");
        address2.setState("Karnataka");
        address2.setZipCode("3456788");
        address2.setCountry("India");
        userEntity.setAddress(address1);
        
        when(userRepository.findById(1)).thenReturn(Optional.of(userEntity));

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
        User updatedUser = invocation.getArgument(0);
        return updatedUser;
        });

        UserDto updatedUserDto = userServiceImpl.updateUser(userDto, 1);
        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(1)).save(any(User.class));
        assertEquals(1, updatedUserDto.getId());
        assertEquals("John Doe", updatedUserDto.getFirstName());
        assertEquals("johndoe@example.com", updatedUserDto.getEmail());
        assertEquals("newpassword", updatedUserDto.getPassword());
        assertEquals("Navya", updatedUserDto.getLastName());
        assertEquals("9085753478", updatedUserDto.getPhoneNumber());
	}
	
	@Test
	@Order(5)
	void deleteUserTest() {
        User userEntity = new User();
        userEntity.setId(1);

        when(userRepository.findById(1)).thenReturn(Optional.of(userEntity));
        userServiceImpl.deleteUser(1);
        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(1)).delete(userEntity);
    }

	@Test
	@Order(6)
	//User exists for status true
    void getStatusByIdTest() {
        Integer userId = 1;
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setStatus(true);
        //specifies that when the method is called with the provided userId, 
        //it should return an Optional containing the existingUser.
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(existingUser));
        boolean status = userServiceImpl.getStatusById(userId);
        assertTrue(status);
       
    }	
	@Test
	@Order(7)
	// User  exists for  status false(Negative case )
    void getStatusByITestFalse() {
    
        Integer userId = 1;
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setStatus(false);
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(existingUser));
        boolean status = userServiceImpl.getStatusById(userId);
        assertFalse(status);  
    }	
	 @Test
	 @Order(8)
	 //User activation test postive case
	    void userActivationInactiveTest() {
	        Integer userId = 1;
	        User inactiveUser = new User();
	        inactiveUser.setId(userId);
	        inactiveUser.setStatus(false);
	        when(userRepository.findById(userId)).thenReturn(Optional.of(inactiveUser));
	        userServiceImpl.userActivation(userId);
	        assertTrue(inactiveUser.isStatus()); // Assert that the user status is now true
	       }	
	    @Test
	    @Order(9)
	    //User Exits for active user (deactivation)
	    void userDeactivationActiveUser() {
	        Integer userId = 1;
	        User activeUser = new User();
	        activeUser.setId(userId);
	        activeUser.setStatus(true);
	        when(userRepository.findById(userId)).thenReturn(Optional.of(activeUser));
	        userServiceImpl.userDeactivation(userId);
	        assertFalse(activeUser.isStatus()); 
	    }
	  
	    @Test
	    @Order(10)
	    //User not created (exception) it is null values
	    void CreateUserTestNullException() {
	        UserDto userDto = new UserDto();
	        userDto.setId(1);
			userDto.setFirstName(null);
			userDto.setEmail(null);
			userDto.setPassword(null);
			userDto.setLastName(null);
			userDto.setPhoneNumber(null);
	        Address address = new Address();
	        address.setStreet(null);
	        address.setCity(null);
	        address.setState(null);
	        address.setZipCode(null);
	        address.setCountry(null);
	        userDto.setAddress(address);
	        when(userRepository.save(any(User.class))).thenReturn(null);
	        assertThrows(UserCreationException.class, () -> {
	            userServiceImpl.createUser(userDto);
	        });
	    }    
	    @Test
	    @Order(11)
	    //Failed to create user  for runtime exception
	    void CreateUserTestUserCreationException() {
	        UserDto userDto = new UserDto();
	        userDto.setId(1);
			userDto.setFirstName("John");
			userDto.setEmail("john@gmail.com");
			userDto.setPassword("abcd");
			userDto.setLastName("Doe");
			userDto.setPhoneNumber("9085753478");
	        Address address = new Address();
	        address.setStreet("shantinagara cross");
	        address.setCity("derebail");
	        address.setState("Karnataka");
	        address.setZipCode("3456788");
	        address.setCountry("India");
	        userDto.setAddress(address);
	        when(userRepository.save(any(User.class))).thenThrow(RuntimeException.class);
	        assertThrows(UserCreationException.class, () -> {
	        	userServiceImpl.createUser(userDto);
	        });
	    }

	    @Test
	    @Order(12)
        // Set a required field in the Address to null
	    void createUserWithInvalidAddressTest() {
	        UserDto userDto = getValidUserDto();
	        userDto.getAddress().setStreet(null);
	        assertThrows(UserCreationException.class, () -> {
	            userServiceImpl.createUser(userDto);
	        });
	    }
         
	    @Test
	    @Order(13)
	    // Set a required field in the Email to null
	    void createUserWithInvalidEmailTest() {
	        UserDto userDto = getValidUserDto();
	        userDto.setEmail(null);

	        assertThrows(UserCreationException.class, () -> {
	            userServiceImpl.createUser(userDto);
	        });
	    }
        
	    @Test
	    @Order(14)
	    // Set a required field in the PhoneNumber to null
	    void createUserWithInvalidPhoneNumber() {
	        UserDto userDto = getValidUserDto();
	        userDto.setPhoneNumber(null);
	        assertThrows(UserCreationException.class, () -> {
	            userServiceImpl.createUser(userDto);
	        });
	    }
	    
	    @Test
	    @Order(15)
	    // Set a required field in the FirstName to null
	    void createUserWithInvalidFirstName() {
	        UserDto userDto = getValidUserDto();
	        userDto.setFirstName(null);
	        assertThrows(UserCreationException.class, () -> {
	            userServiceImpl.createUser(userDto);
	        });
	    }
	    
	    @Test
	    @Order(16)
	    void createUserWithInvalidLastName() {
	        UserDto userDto = getValidUserDto();
	        // Set a required field in the LastName to null
	        userDto.setLastName(null);
	        assertThrows(UserCreationException.class, () -> {
	            userServiceImpl.createUser(userDto);
	        });
	    }
	    
	    @Test
	    @Order(17)
	    void createUserWithInvalidpassword() {
	        UserDto userDto = getValidUserDto();
	        // Set a required field in the password to null
	        userDto.setPassword(null);
	        assertThrows(UserCreationException.class, () -> {
	            userServiceImpl.createUser(userDto);
	        });
	    }

	    @Test
	    @Order(18)
	    //Test case for  having duplicate email id
	    void createUserTestDuplicateEmail() {
	        UserDto userDto = new UserDto();
	        userDto.setId(1);
	        userDto.setFirstName("John");
	        userDto.setEmail("john@gmail.com");

	        UserDto userDto1 = new UserDto();
	        userDto1.setId(2);
	        userDto1.setFirstName("John");
	        userDto1.setEmail("john@gmail.com");
	        //mock the duplicate email
	        when(userRepository.existsByEmail("john@gmail.com")).thenReturn(true);
	        assertThrows(DuplicationException.class, () -> {
	            userServiceImpl.createUser(userDto1);
	        });
	    }

	    @Test
	    @Order(19)
	    //Test case for  having duplicate phone number
	    void CreateUserTestDuplicatePhoneNumber() {
	        UserDto userDto = new UserDto();
	        userDto.setFirstName("John");
	        userDto.setEmail("Navyarao42@gmail.com");
	        userDto.setPhoneNumber("9972850754");
	        UserDto userDto1 = new UserDto();
	        userDto1.setFirstName("Navya");
	        userDto1.setEmail("john42@gmail.com");
	        userDto1.setPhoneNumber("9972850754");
	        when(userRepository.existsByEmail("john42@gmail.com")).thenReturn(false);
	        when(userRepository.existsByPhoneNumber("9972850754")).thenReturn(true);
	        assertThrows(DuplicationException.class, () -> {
	            userServiceImpl.createUser(userDto);
	        });
	    }

	    @Test
	    @Order(20)
	    // no user found with that id 
	    void getUserByIdTestUserNotFound() {
	        int userId = 9;
	        when(userRepository.findById(anyInt())).thenReturn(java.util.Optional.empty());
	        assertThrows(UserNotFoundException.class, () -> {
	            userServiceImpl.getUserById(userId);
	        });
	        
	    } 
	    @Test
	    @Order(21)
	    // failing to fetch the user details by id
	    void getUserByIdTestUserFetchException() {
	        int userId = 10;
	        when(userRepository.findById(anyInt())).thenThrow(RuntimeException.class);
	        assertThrows(UserFetchException.class, () -> {
	            userServiceImpl.getUserById(userId);
	        });
	    }
	    
	    @Test
	    @Order(22)
	    //failing to fetch all the user details
	    void getAllUsersTestUserFetchException() {
	        when(userRepository.findAll()).thenThrow(RuntimeException.class);
	        assertThrows(UserFetchException.class, () -> {
	            userServiceImpl.getAllUsers();
	        });

	    }
	    
	    @Test
	    @Order(23)
	    //if here is no user   if it is empty
	    void getAllUsersTestException() {
	        when(userRepository.findAll()).thenReturn(Collections.emptyList());
	        assertThrows(ResourceNotFoundException.class, () -> {
	            userServiceImpl.getAllUsers();
	        });
	    }
	    
	    @Test
	    @Order(24)
	    //// no user found with that id 
	    void DeleteUserTestUserNotFound() {
	        int userId = 1;
	        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());
	        assertThrows(UserNotFoundException.class, () -> {
	            userServiceImpl.deleteUser(userId);
	        });
	    }
	    
	    @Test
	    @Order(25)
	    //failed to delete the user
	    void DeleteUserTestUserDeletionException() {
	        int userId = 1;
	        when(userRepository.findById(anyInt())).thenReturn(Optional.of(new User()));
	        doThrow(RuntimeException.class).when(userRepository).delete(any(User.class));
	        assertThrows(UserDeletionException.class, () -> {
	            userServiceImpl.deleteUser(userId);
	        });
	    }
	    
	    @Test
	    @Order(26)
	    // updating user with no user id not found
	    void UpdateUserTestUserNotFoundException() {
	        int userId = 1;
	        UserDto userDto = new UserDto();
	        userDto.setId(1);
	        userDto.setFirstName("Navya rao");
	        userDto.setEmail("Navyarao@example.com");
	        userDto.setPassword("234798765");
	        userDto.setLastName("sahana");
	        userDto.setPhoneNumber("7890054466");
	        
	        Address address1 = new Address();
	        address1.setStreet("Nandigudda cross");
	        address1.setCity("chennai");
	        address1.setState("Tamil nadu");
	        address1.setZipCode("34568888");
	        address1.setCountry("India");
	        userDto.setAddress(address1);
	        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());
	        assertThrows(UserNotFoundException.class, () -> {
	            userServiceImpl.updateUser(userDto, userId);
	        });

	    }
	    @Test
	    @Order(27)
	 // Create a valid UserDto and an existing userId with one field null 
	    void updateWithInvalidUserDtoTest() {

	        UserDto invalidUserDto = new UserDto();
	        invalidUserDto.setFirstName(null);
	        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());
	        assertThrows(UserNotFoundException.class, () -> {
	            userServiceImpl.updateUser(invalidUserDto, 1);
	        });
	        verify(userRepository, times(1)).findById(1);
	        verify(userRepository, never()).save(any(User.class));
	    }

	    @Test
	    @Order(28)
	    // failed to update exception
	    void updateUserWithExceptionDuringUpdateTest() {
	        UserDto userDto = getValidUserDto();
	        Integer userId = 1;
	        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
	        when(userRepository.save(any(User.class))).thenThrow(UserUpdateException.class);
	        assertThrows(UserUpdateException.class, () -> userServiceImpl.updateUser(userDto, userId));
	    }
	    
	    @Test
	    @Order(29)
	    //user not found with id
	     void getStatusByIdTestUserNotFound() {
	        Integer userId = 999; 
	        
	        when(userRepository.findById(userId)).thenReturn(Optional.empty());
	        assertThrows(UserNotFoundException.class, () -> {
	                userServiceImpl.getStatusById(userId);
	        });
	        
	    }
	    
	    @Test
	    @Order(30)
	    //User  whose status is false 
	     void getStatusByIdTest_Success() {
	        Integer userId = 1;
	        User existingUser =  getValidUser();
	        existingUser.setStatus(true);
	        
	        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
	        boolean status = userServiceImpl.getStatusById(userId);
	        assertTrue(status);
	        verify(userRepository, times(1)).findById(userId);
	        
	    }

	    @Test
	    @Order(31)
	    // user fetch exception 
	     void getStatusByIdTestException() {
	        Integer userId = 1;
	        when(userRepository.findById(userId)).thenThrow(new RuntimeException("Simulated exception"));
	        assertThrows(UserFetchException.class,  () -> {
	        userServiceImpl.getStatusById(userId);
	        });

	      
	    }
	    
	    @Test
	    @Order(32)
	 // Creating a valid userId for an already active user
	    void userActivationAlreadyActiveUserTest() {
	        Integer userId = 1;
	        User activeUser = new User();
	        activeUser.setStatus(true);
	        when(userRepository.findById(userId)).thenReturn(Optional.of(activeUser));
	        assertThrows(UserAlreadyActiveException.class, () -> userServiceImpl.userActivation(userId));
	    }
	    
	    @Test
	    @Order(33)
	    //activating the user with id not  present
	    void userActivationNonExistingUserTest() {
	        Integer nonExistingUserId = 99;
	        when(userRepository.findById(nonExistingUserId)).thenReturn(Optional.empty());
	        assertThrows(UserNotFoundException.class, () -> userServiceImpl.userActivation(nonExistingUserId));
	    }
	    
	    @Test
	    @Order(34)
	    // activating the user but exception while failure
	    void userActivationExceptionDuringActivationTest() {
	        Integer userId = 1;
	        User inactiveUser = new User();
	        inactiveUser.setStatus(false);
	        when(userRepository.findById(userId)).thenReturn(Optional.of(inactiveUser));
	        when(userRepository.save(any(User.class))).thenThrow(UserActivationException.class);
	        assertThrows(UserActivationException.class, () -> userServiceImpl.userActivation(userId));
	    }

	    @Test
	    @Order(35)
        // Create a valid userId for an already inactive user
	    void userDeactivationAlreadyInactiveUserTest() {
	        Integer userId = 1;
	        User inactiveUser = new User();
	        inactiveUser.setStatus(false);
	        when(userRepository.findById(userId)).thenReturn(Optional.of(inactiveUser));
	        assertThrows(UserAlreadyInactiveException.class, () -> userServiceImpl.userDeactivation(userId));
	    }
	    @Test
	    @Order(36)
	    // Create an invalid userId for a non-existing user
	    void userDeactivationNonExistingUserTest() {
	       Integer nonExistingUserId = 99;
	       when(userRepository.findById(nonExistingUserId)).thenReturn(Optional.empty());
           assertThrows(UserNotFoundException.class, () -> userServiceImpl.userDeactivation(nonExistingUserId));
	    }
	    
	    @Test
	    @Order(37)
	 // Create a valid userId for an active user
	    void userDeactivationExceptionDuringDeactivationTest() {
	       Integer userId = 1;
           User activeUser = new User();
	       activeUser.setStatus(true);
	       when(userRepository.findById(userId)).thenReturn(Optional.of(activeUser));
	       when(userRepository.save(any(User.class))).thenThrow(UserActivationException.class);
           assertThrows(UserDeactivationException.class, () -> userServiceImpl.userDeactivation(userId));
	    }
	    
	    @Test
	    @Order(38)
	    //Fetching all active users(Positive test case)
	    void getAllActiveUsers_Success() {
	    	 User user = getValidUser();
	    	 user.setStatus(true);
	    	 User user1 = new User();
			    user1.setId(1);
			    user1.setFirstName("John");
			    user1.setEmail("john@gmail.com");
			    user1.setPassword("abcd");
			    user1.setLastName("Doe");
			    user1.setPhoneNumber("9085753478");
			    Address address = new Address();
			    address.setStreet("shantinagara cross");
			    address.setCity("derebail");
			    address.setState("Karnataka");
			    address.setZipCode("3456788");
			    address.setCountry("India");
			    user1.setAddress(address);
			    user1.setStatus(true);
	        List<User> activeUsers = List.of(user,user1);
	        when(userRepository.findByStatus(true)).thenReturn(activeUsers);
	        List<UserDto> result = userServiceImpl.getAllActiveUsers();
	        assertEquals(activeUsers.size(), result.size());
	    }
	    
	    @Test
	    @Order(39)
	    //it returns an empty list (indicating no active users). 
	    void getAllActiveUsers_NoActiveUsers() {
	    	UserDto userDto = new UserDto();
	        when(userRepository.findByStatus(true)).thenReturn(Collections.emptyList());
	        assertThrows(ResourceNotFoundException.class, () -> userServiceImpl.getAllActiveUsers());
	    }
	    
	    @Test
	    @Order(40)
	    //User fetch exception
	    void getAllActiveUsersGeneralException() {
	        when(userRepository.findByStatus(true)).thenThrow(RuntimeException.class);
	        assertThrows(UserFetchException.class, () -> userServiceImpl.getAllActiveUsers());
	    }
	    
	   

		private UserDto getValidUserDto() {
		    UserDto userDto = new UserDto();
		    userDto.setId(1);
		    userDto.setFirstName("John");
		    userDto.setEmail("john@gmail.com");
		    userDto.setPassword("abcd");
		    userDto.setLastName("Doe");
		    userDto.setPhoneNumber("9085753478");

		    Address address = new Address();
		    address.setStreet("shantinagara cross");
		    address.setCity("derebail");
		    address.setState("Karnataka");
		    address.setZipCode("3456788");
		    address.setCountry("India");
		    userDto.setAddress(address);

		    return userDto;
		}
		
		private User getValidUser() {
	        User existingUser =  new User();
	        existingUser.setId(1);
	        existingUser.setFirstName("Navya rao");
	        existingUser.setEmail("Navyarao@example.com");
	        existingUser.setPassword("234798765");
	        existingUser.setLastName("sahana");
	        existingUser.setPhoneNumber("7890054466");
	        
	        Address address1 = new Address();
	        address1.setStreet("Nandigudda cross");
	        address1.setCity("chennai");
	        address1.setState("Tamil nadu");
	        address1.setZipCode("34568888");
	        address1.setCountry("India");
	        existingUser.setAddress(address1);
	        
	        return existingUser;
		}		
}


