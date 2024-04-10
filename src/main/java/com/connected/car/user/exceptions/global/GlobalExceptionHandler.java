package com.connected.car.user.exceptions.global;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.connected.car.user.exceptions.custom.DatabaseConnectionException;
import com.connected.car.user.exceptions.custom.DuplicationException;
import com.connected.car.user.exceptions.custom.InvalidInputException;
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
import com.connected.car.user.payload.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

	// For Handling Resource not found exception
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> handlerResourceNotFoundException(ResourceNotFoundException ex) {
		String responseMessage = ex.getMessage();
		ApiResponse response = ApiResponse.builder().responseSuccess(true).responseMessage(responseMessage)
				.responseStatus(HttpStatus.NOT_FOUND).build();
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	// For Handling User not found exception
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ApiResponse> handlerUserNotFoundException(UserNotFoundException ex) {
		String responseMessage = ex.getMessage();
		ApiResponse response = ApiResponse.builder().responseSuccess(true).responseMessage(responseMessage)
				.responseStatus(HttpStatus.NOT_FOUND).build();
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	// For Handling invalid input exception
	@ExceptionHandler(InvalidInputException.class)
	public ResponseEntity<ApiResponse> handlerInvalidInputException(InvalidInputException ex) {
		String responseMessage = ex.getMessage();
		ApiResponse response = ApiResponse.builder().responseSuccess(false).responseMessage(responseMessage)
				.responseStatus(HttpStatus.BAD_REQUEST).build();
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	// For Handling the Exception while establishing the connection with the
	// database
	@ExceptionHandler(DatabaseConnectionException.class)
	public ResponseEntity<String> handleDatabaseConnectionException(DatabaseConnectionException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// For Handling the Exception for Validation
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse> handleValidationException(MethodArgumentNotValidException ex) {

		String responseMessage = "Validation error: " + ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
		ApiResponse response = ApiResponse.builder().responseSuccess(true).responseMessage(responseMessage)
				.responseStatus(HttpStatus.NOT_ACCEPTABLE).build();
		return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
	}
	// For Handling the Exception when there is an error in activating the user
	@ExceptionHandler(UserActivationException.class)
	public ResponseEntity<ApiResponse> handlerUserActivationException(UserActivationException ex) {
		String responseMessage = ex.getMessage();
		ApiResponse response = ApiResponse.builder().responseSuccess(true).responseMessage(responseMessage)
				.responseStatus(HttpStatus.INTERNAL_SERVER_ERROR).build();
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// For Handling the Exception when the user is already active
	@ExceptionHandler(UserAlreadyActiveException.class)
	public ResponseEntity<ApiResponse> handlerUserAlreadyActiveException(UserAlreadyActiveException ex) {
		String responseMessage = ex.getMessage();
		ApiResponse response = ApiResponse.builder().responseSuccess(true).responseMessage(responseMessage)
				.responseStatus(HttpStatus.BAD_REQUEST).build();
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	// For Handling the Exception when the user is already inactive
	@ExceptionHandler(UserAlreadyInactiveException.class)
	public ResponseEntity<ApiResponse> handlerUserAlreadyInactiveException(UserAlreadyInactiveException ex) {
		String responseMessage = ex.getMessage();
		ApiResponse response = ApiResponse.builder().responseSuccess(true).responseMessage(responseMessage)
				.responseStatus(HttpStatus.BAD_REQUEST).build();
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	// For Handling the Exception while creating the user
	@ExceptionHandler(UserCreationException.class)
	public ResponseEntity<ApiResponse> handlerUserCreationException(UserCreationException ex) {
		String responseMessage = ex.getMessage();
		ApiResponse response = ApiResponse.builder().responseSuccess(true).responseMessage(responseMessage)
				.responseStatus(HttpStatus.NOT_ACCEPTABLE).build();
		return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
	}

	// For Handling the Exception while deactivating the user
	@ExceptionHandler(UserDeactivationException.class)
	public ResponseEntity<ApiResponse> handlerUserDeactivationException(UserDeactivationException ex) {
		String responseMessage = ex.getMessage();
		ApiResponse response = ApiResponse.builder().responseSuccess(true).responseMessage(responseMessage)
				.responseStatus(HttpStatus.BAD_REQUEST).build();
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	// For Handling the Exception while deleting the user
	@ExceptionHandler(UserDeletionException.class)
	public ResponseEntity<ApiResponse> handlerUserDeletionException(UserDeletionException ex) {
		String responseMessage = ex.getMessage();
		ApiResponse response = ApiResponse.builder().responseSuccess(true).responseMessage(responseMessage)
				.responseStatus(HttpStatus.BAD_REQUEST).build();
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	// For Handling the Exception while fetching the details of user.
	@ExceptionHandler(UserFetchException.class)
	public ResponseEntity<ApiResponse> handlerUserDeletionException(UserFetchException ex) {
		String responseMessage = ex.getMessage();
		ApiResponse response = ApiResponse.builder().responseSuccess(true).responseMessage(responseMessage)
				.responseStatus(HttpStatus.BAD_REQUEST).build();
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	// For Handling the Exception while Updating the user.
	@ExceptionHandler(UserUpdateException.class)
	public ResponseEntity<ApiResponse> handlerUserUpdateException(UserUpdateException ex) {
		String responseMessage = ex.getMessage();
		ApiResponse response = ApiResponse.builder().responseSuccess(true).responseMessage(responseMessage)
				.responseStatus(HttpStatus.INTERNAL_SERVER_ERROR).build();
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// For Handling general exceptions
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse> handlerException(Exception e) {
		String responseMessage = "An error occurred: " + e.getMessage();
		ApiResponse response = ApiResponse.builder().responseSuccess(false).responseMessage(responseMessage)
				.responseStatus(HttpStatus.INTERNAL_SERVER_ERROR).build();
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	// For Handling the Exception while creating the user
		@ExceptionHandler(DuplicationException.class)
		public ResponseEntity<ApiResponse> handlerDuplicateEmailException(DuplicationException ex) {
			String responseMessage = ex.getMessage();
			ApiResponse response = ApiResponse.builder().responseSuccess(true).responseMessage(responseMessage)
					.responseStatus(HttpStatus.CONFLICT).build();
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}

}