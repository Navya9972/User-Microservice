//package com.connected.car.user.controller;
//
//import static org.hamcrest.CoreMatchers.is;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.doThrow;
//import static org.mockito.Mockito.never;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.security.Principal;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.ClassOrderer.OrderAnnotation;
//import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.keycloak.KeycloakPrincipal;
//import org.keycloak.KeycloakSecurityContext;
//import org.keycloak.adapters.OidcKeycloakAccount;
//import org.keycloak.adapters.spi.KeycloakAccount;
//import org.keycloak.adapters.springsecurity.account.KeycloakRole;
//import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
//import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
//import org.keycloak.representations.AccessToken;
//import org.mockito.InjectMocks;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.ResultMatcher;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.result.StatusResultMatchers;
//
//import com.connected.car.user.constants.ResponseMessage;
//import com.connected.car.user.dto.UserDto;
//import com.connected.car.user.entity.Address;
//import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth;
//import com.connected.car.user.constants.*;
//import com.connected.car.user.entity.ApiResponse;
//import com.connected.car.user.entity.User;
//import com.connected.car.user.exceptions.custom.ResourceNotFoundException;
//import com.connected.car.user.exceptions.global.*;
//import com.connected.car.user.exceptions.custom.UserNotFoundException;
//import com.connected.car.user.exceptions.custom.UserFetchException;
//import com.connected.car.user.exceptions.custom.UserUpdateException;
//import com.connected.car.user.repository.UserRepository;
//import com.connected.car.user.service.UserService;
//import com.connected.car.user.service.impl.UserServiceImpl;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import ch.qos.logback.core.status.Status;
//
//@WebMvcTest(UserController.class)
//@AutoConfigureMockMvc
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//class UserControllerTest {
//
//	@InjectMocks
//	private UserController userController;
//
//	@MockBean
//	private UserRepository userRepository;
//
//	@Autowired
//	private ObjectMapper objectMapper;
//
//	@MockBean
//	private UserService userService;
//
//	@Autowired
//	private MockMvc mockMvc;
//
//	@BeforeEach
//	public void init() {
//		MockitoAnnotations.initMocks(this);
//	}
//	
//
//	
//	@Test
//	@Order(1)
//	//@WithMockKeycloakAuth
//	void createUserTest() throws Exception {
//	    UserDto createdUser = new UserDto();
//	    String userDtoJson = objectMapper.writeValueAsString(createdUser);
//	    when(userService.createUser(any(UserDto.class))).thenReturn(createdUser);
//	    mockMvc.perform(MockMvcRequestBuilders.post("/v1/api/users/create")
//	            .contentType(MediaType.APPLICATION_JSON)
//	            .content(userDtoJson) 
//	            .accept(MediaType.APPLICATION_JSON))
//	            .andExpect(MockMvcResultMatchers.status().isCreated());
//	}
//
//	
//	
//	
//    @Test
//    @Order(2)
//    @WithMockKeycloakAuth("admin")
//    void updateUserTest() throws Exception {
//        Integer userId = 1;
////        UserDto userDto = new UserDto();
////        userDto.setId(userId);
////        userDto.setFirstName("John");
////        userDto.setLastName("Doe");
////        userDto.setPassword("N@212845rao");
////        userDto.setEmail("user@gmail.com");
////        userDto.setPhoneNumber("123456789");
////        userDto.setStatus(true);
////        Address address = new Address();
////        address.setCity("bangalore");
////        address.setCountry("india");
////        address.setState("karnataka");
////        address.setStreet("domlur");
////        address.setZipCode("560071");
////        userDto.setAddress(address);
//
//        UserDto updatedUserDto = new UserDto();
////        updatedUserDto.setId(2);
////        updatedUserDto.setFirstName("John");
////        updatedUserDto.setLastName("Doe");
////        updatedUserDto.setPassword("N@212845rao");
////        updatedUserDto.setEmail("user@gmail.com");
////        updatedUserDto.setPhoneNumber("123456789");
////        updatedUserDto.setStatus(true);
////        Address address2 = new Address();
////        address2.setCity("bangalore");  // Corrected: use address2 for updatedUserDto
////        address2.setCountry("india");
////        address2.setState("karnataka");
////        address2.setStreet("domlur");
////        address2.setZipCode("560071");
////        updatedUserDto.setAddress(address2);
//
//        when(userService.updateUser(eq(), eq(userId))).thenReturn(updatedUserDto);
//        mockMvc.perform(MockMvcRequestBuilders.put("/v1/api/users/update/{userId}", userId)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(userDto)))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
// 
//    @Test
//    @Order(2)
//    @WithMockKeycloakAuth("ADMIN")
//    void updateUserTest1() throws Exception {
//        Integer userId = 1;
//
//        // Original user data to be updated
//        UserDto userDto = new UserDto();
//        userDto.setId(userId);
//        userDto.setFirstName("John");
//        userDto.setLastName("Doe");
//        userDto.setPassword("N@212845rao");
//        userDto.setEmail("user@gmail.com");
//        userDto.setPhoneNumber("123456789");
//        userDto.setStatus(true);
//        Address address = new Address();
//        address.setCity("Bangalore");
//        address.setCountry("India");
//        address.setState("Karnataka");
//        address.setStreet("Domlur");
//        address.setZipCode("560071");
//        userDto.setAddress(address);
//
//        // Updated user data
//        UserDto updatedUserDto = new UserDto();
//        updatedUserDto.setId(userId);
//        updatedUserDto.setFirstName("UpdatedJohn");
//        updatedUserDto.setLastName("UpdatedDoe");
//        updatedUserDto.setPassword("N@212845rao");
//        updatedUserDto.setEmail("updateduser@gmail.com");
//        updatedUserDto.setPhoneNumber("987654321");
//        updatedUserDto.setStatus(true);
//        Address updatedAddress = new Address();
//        updatedAddress.setCity("UpdatedBangalore");
//        updatedAddress.setCountry("India");
//        updatedAddress.setState("UpdatedKarnataka");
//        updatedAddress.setStreet("UpdatedDomlur");
//        updatedAddress.setZipCode("560072");
//        updatedUserDto.setAddress(updatedAddress);
//
//        // Mocking the userService to return the updated user when updateUser is called
//        when(userService.updateUser(any(UserDto.class), eq(userId))).thenReturn(updatedUserDto);
//
//        // Performing the PUT request to update the user
//        mockMvc.perform(MockMvcRequestBuilders.put("/v1/api/users/update/{userId}", userId)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(userDto))) // Using the original user data for the update
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
//
//
//	 
//	 
//	
//
//    @Test
//    @Order(3)
//    @WithMockKeycloakAuth("ADMIN")
//    void deleteUserTest() throws Exception {
//        Integer userId = 1;
//        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/api/users/delete/{userId}", userId))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andReturn();
//        verify(userService, times(1)).deleteUser(userId);
//    }
//	 
//	
//
////    @Test
////    @Order(4)
////    void getAllUsersTest() throws Exception {
////        List<UserDto> userList =new ArrayList<>();
////        UserDto userDto =new UserDto();
////        userDto.setId(1);
////        userDto.setFirstName("John");
////        userDto.setLastName("Doe");
////        userDto.setPassword("abcd");
////        userDto.setEmail("user@gmail.com");
////        userDto.setPhoneNumber("123456789");
////        userDto.setStatus(true);
////        Address address=new Address();
////        address.setCity("bangalore");
////        address.setCountry("india");
////        address.setState("karnataka");
////        address.setStreet("domlur");
////        address.setZipCode("560071");
////        userDto.setAddress(address);
////
////        UserDto userDto2 =new UserDto();
////        userDto2.setId(2);
////        userDto2.setFirstName("Michael");
////        userDto2.setLastName("Jackson");
////        userDto2.setPassword("abcd");
////        userDto2.setEmail("user@gmail.com");
////        userDto2.setPhoneNumber("123456789");
////        userDto2.setStatus(true);
////        Address address2=new Address();
////        address.setCity("bangalore");
////        address.setCountry("india");
////        address.setState("karnataka");
////        address.setStreet("domlur");
////        address.setZipCode("560071");
////        userDto2.setAddress(address2);
////        userList.add(userDto);
////        userList.add(userDto2);
////        when(userService.getAllUsers()).thenReturn(userList);
////
//// 
////
////        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/users/getAllUsers"))
////                .andExpect(MockMvcResultMatchers.status().isOk());
////        verify(userService, times(1)).getAllUsers();
////    }
//   
//
////    @Test
////    @Order(5)
////    void getUserTest() throws Exception {
////        Integer userId = 1;
////        UserDto userDto =new UserDto();
////        userDto.setId(userId);
////        userDto.setFirstName("John");
////        userDto.setLastName("Doe");
////        userDto.setPassword("N@212845rao");
////        userDto.setEmail("user@gmail.com");
////        userDto.setPhoneNumber("123456789");
////        userDto.setStatus(true);
////        Address address=new Address();
////        address.setCity("bangalore");
////        address.setCountry("india");
////        address.setState("karnataka");
////        address.setStreet("domlur");
////        address.setZipCode("560071");
////        userDto.setAddress(address);
////        when(userService.getUserById(userId)).thenReturn(userDto);
////        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/users/getUser/{userId}", userId))
////                .andExpect(MockMvcResultMatchers.status().isOk());
////        verify(userService, times(1)).getUserById(eq(userId));
////    }
//    
//    
////    
////    @Test
////    @Order(6)
////    void getStatusByIdTest_ActiveStatus() throws Exception {
////        Integer userId = 1;
////        when(userService.getStatusById(userId)).thenReturn(true);
////        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/users/getStatus/{userId}", userId))
////                .andExpect(MockMvcResultMatchers.status().isOk());
////        verify(userService, times(1)).getStatusById(eq(userId));
////  }
//    
//    
//
////    @Test
////    @Order(7)
////    void getStatusByIdTest_InactiveStatus() throws Exception {
////        Integer userId = 1;
////        when(userService.getStatusById(userId)).thenReturn(false);
////        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/users/getStatus/{userId}", userId))
////                .andExpect(MockMvcResultMatchers.status().isOk());
////        verify(userService, times(1)).getStatusById(eq(userId));
////    }
//    
//    
////    @Test
////    @Order(8)
////    void UpdateStatusToInactiveTest() throws Exception {
////        Integer userId = 1;
////        mockMvc.perform(MockMvcRequestBuilders.put("/v1/api/users/InactiveStatus/{userId}", userId))
////                .andExpect(MockMvcResultMatchers.status().isOk());
////        verify(userService, times(1)).userDeactivation(eq(userId));
////    }
//    
//    
//    
//
////    @Test
////    @Order(9)
////    void UpdateStatusToActiveTest() throws Exception {
////        Integer userId = 1;
////        mockMvc.perform(MockMvcRequestBuilders.put("/v1/api/users/activeStatus/{userId}", userId))
////                .andExpect(MockMvcResultMatchers.status().isOk());
////        verify(userService, times(1)).userActivation(eq(userId));
////    }
//    
//   
//
//    
//    
////    @Test
////    @Order(10)
////   void getAllActiveUsersTest() throws Exception {
////	  UserDto userDto =new UserDto();
////     userDto.setId(1);
////     userDto.setFirstName("John");
////     userDto.setLastName("Doe");
////     userDto.setPassword("N@212845rao");
////     userDto.setEmail("user@gmail.com");
////     userDto.setPhoneNumber("123456789");
////     userDto.setStatus(true);
////     Address address=new Address();
////     address.setCity("bangalore");
////     address.setCountry("india");
////     address.setState("karnataka");
////     address.setStreet("domlur");
////     address.setZipCode("560071");
////     userDto.setAddress(address);
////     UserDto userDto2 =new UserDto();
////     userDto2.setId(2);
////     userDto2.setFirstName("Michael");
////    userDto2.setLastName("Jackson");
////    userDto2.setPassword("N@212845rao");
////    userDto2.setEmail("user@gmail.com");
////    userDto2.setPhoneNumber("123456789");
////    userDto2.setStatus(true);
////     Address address2=new Address();
////    address2.setCity("bangalore");
////    address2.setCountry("india");
////    address2.setState("karnataka");
////    address2.setStreet("domlur");
////    address2.setZipCode("560071");
////    userDto2.setAddress(address2);
////   
////         List<UserDto> mockUserDetails = Arrays.asList(userDto,userDto2);
////          when(userService.getAllActiveUsers()).thenReturn(mockUserDetails);
////          mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/users/getAllActiveUsers"))
////            .andExpect(MockMvcResultMatchers.status().isOk());
////             verify(userService, times(1)).getAllActiveUsers();
////}	
//    
//    
//   
//
////
////  @Test
////  @Order(11)
////  void getAllInActiveUsersTest() throws Exception {
////	UserDto userDto = new UserDto();
////	userDto.setId(1);
////	userDto.setFirstName("John");
////	userDto.setEmail("john@gmail.com");
////	userDto.setPassword("N@212845rao");
////	userDto.setLastName("Doe");
////	userDto.setPhoneNumber("9085753478");
////
////	Address address = new Address();
////	address.setStreet("shantinagara cross");
////	address.setCity("derebail");
////	address.setState("Karnataka");
////	address.setZipCode("3456788");
////	address.setCountry("India");
////	userDto.setAddress(address);
////	userDto.setStatus(false);
////	
////    UserDto userDto2 =new UserDto();
////    userDto2.setId(2);
////    userDto2.setFirstName("Michael");
////    userDto2.setLastName("Jackson");
////    userDto2.setPassword("N@212845rao");
////    userDto2.setEmail("user@gmail.com");
////    userDto2.setPhoneNumber("123456789");
////    userDto2.setStatus(false);
////    Address address2=new Address();
////    address2.setCity("bangalore");
////    address2.setCountry("india");
////    address2.setState("karnataka");
////    address2.setStreet("domlur");
////    address2.setZipCode("560071");
////    userDto2.setAddress(address2);
////    
////    List<UserDto> mockUserDetails = Arrays.asList(userDto,userDto2);
////    when(userService.getAllInactiveUsers()).thenReturn(mockUserDetails);
////    mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/users/getAllInactiveUsers"))
////             .andExpect(MockMvcResultMatchers.status().isOk());
////              verify(userService, times(1)).getAllInactiveUsers();
////}	
//  
//
//
//}