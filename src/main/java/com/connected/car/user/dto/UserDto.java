package com.connected.car.user.dto;


import com.connected.car.user.entity.Address;

import jakarta.persistence.Embedded;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	
	private int id;
	
    @NotBlank(message = "First name is required")
	private String firstName;
    
	private String lastName;
	
	@Email
	@NotBlank(message="Email is required")
	private String email;
	
	@Pattern(regexp = "^\\d{7,15}$",message="PhoneNumber must be between 7 and 15 digits in length")
	@NotBlank(message = "Phone number is required")
	private String phoneNumber;
	
	@Embedded
	private Address address;
	
	private boolean status;
	
    @NotBlank(message = "Password is required")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[@#$%])(?=.*[A-Z]).{6,16}$", message = "Password must contain at least one lowercase letter, one digit, one special character from [@#$%], one uppercase letter, and be between 6 and 16 characters in length")
	private String password;
}
