package com.connected.car.exceptions.global;

	
	import org.junit.jupiter.api.MethodOrderer;
	import org.junit.jupiter.api.Order;
	import org.junit.jupiter.api.Test;
	import org.junit.jupiter.api.TestMethodOrder;
	import org.junit.jupiter.api.extension.ExtendWith;
	import org.mockito.InjectMocks;
	import org.mockito.Mock;
	import org.mockito.Mockito;
	import org.mockito.junit.jupiter.MockitoExtension;
	import org.springframework.http.HttpStatus;
	import org.springframework.http.ResponseEntity;
	import org.springframework.validation.BindingResult;
	import org.springframework.validation.ObjectError;
	import org.springframework.web.bind.MethodArgumentNotValidException;

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
	import com.connected.car.user.exceptions.global.GlobalExceptionHandler;
	import com.connected.car.user.payload.ApiResponse;

	import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

	import java.util.Collections;

	@ExtendWith(MockitoExtension.class)
	@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
     class GlobalExceptionHandlerTest {

	    @InjectMocks
	    private GlobalExceptionHandler globalExceptionHandler;

	    @Mock
	    private ResourceNotFoundException resourceNotFoundException;
	    
	    @Mock
	    private UserNotFoundException usernotfoundexception;
	    
	    @Mock
	    private InvalidInputException invalidInputException;

	    @Mock
	    private DatabaseConnectionException databaseConnectionException;
	    
	    @Mock
	    private MethodArgumentNotValidException validationException;

	    @Mock
	    private UserActivationException userActivationException;

	    @Mock
	    private UserAlreadyActiveException userAlreadyActiveException;
	    
	    @Mock
	    private UserAlreadyInactiveException userAlreadyInactiveException;

	    @Mock
	    private UserCreationException userCreationException;

	    @Mock
	    private UserDeactivationException userDeactivationException;
	    
	    @Mock
	    private UserDeletionException userDeletionException;

	    @Mock
	    private UserFetchException userFetchException;

	    @Mock
	    private UserUpdateException userUpdateException;

	    @Mock
	    private Exception generalException;


	    @Test
	    @Order(1)
	    void testHandlerResourceNotFoundException() {
	    	//mock
	        String errorMessage = "Resource not found!";
	        when(resourceNotFoundException.getMessage()).thenReturn(errorMessage);
	        ResponseEntity<ApiResponse> responseEntity = globalExceptionHandler.handlerResourceNotFoundException(resourceNotFoundException);
	        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	        ApiResponse responseBody = responseEntity.getBody();
	        assertEquals(true, responseBody.isResponseSuccess());
	        assertEquals(errorMessage, responseBody.getResponseMessage());
	        assertEquals(HttpStatus.NOT_FOUND, responseBody.getResponseStatus());
	    }
	    
	    @Test
	    @Order(2)
	    void testHandlerUserNotFoundException() {
	        String errorMessage = "User not found!";
	        when(usernotfoundexception.getMessage()).thenReturn(errorMessage);
	        ResponseEntity<ApiResponse> responseEntity = globalExceptionHandler.handlerUserNotFoundException(usernotfoundexception);
	        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	        ApiResponse responseBody = responseEntity.getBody();
	        assertEquals(true, responseBody.isResponseSuccess());
	        assertEquals(errorMessage, responseBody.getResponseMessage());
	        assertEquals(HttpStatus.NOT_FOUND, responseBody.getResponseStatus());
	    }
	   
	    @Test
	    @Order(3)
	    void testHandlerInvalidInputException() {
	        String errorMessage = "Invalid input!";
	        when(invalidInputException.getMessage()).thenReturn(errorMessage);
	        ResponseEntity<ApiResponse> responseEntity = globalExceptionHandler.handlerInvalidInputException(invalidInputException);
	        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	        ApiResponse responseBody = responseEntity.getBody();
	        assertEquals(false, responseBody.isResponseSuccess());
	        assertEquals(errorMessage, responseBody.getResponseMessage());
	        assertEquals(HttpStatus.BAD_REQUEST, responseBody.getResponseStatus());
	    }

	    @Test
	    @Order(4)
	    void testHandleDatabaseConnectionException() {
	        String errorMessage = "Database connection error!";
	        when(databaseConnectionException.getMessage()).thenReturn(errorMessage);
	        ResponseEntity<String> responseEntity = globalExceptionHandler.handleDatabaseConnectionException(databaseConnectionException);
	        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
	        assertEquals(errorMessage, responseEntity.getBody());
	    }
	    
	    @Test
	    @Order(5)
	    void testHandleValidationException() {
	        // Arrange
	        String errorMessage = "Validation error: Invalid input";
	        BindingResult bindingResult = Mockito.mock(BindingResult.class);
	        when(validationException.getBindingResult()).thenReturn(bindingResult);
	        when(bindingResult.getAllErrors()).thenReturn(Collections.singletonList(new ObjectError("field", "Invalid input")));
	        ResponseEntity<ApiResponse> responseEntity = globalExceptionHandler.handleValidationException(validationException);
	        assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
	        ApiResponse responseBody = responseEntity.getBody();
	        assertEquals(true, responseBody.isResponseSuccess());
	        assertEquals(errorMessage, responseBody.getResponseMessage());
	        assertEquals(HttpStatus.NOT_ACCEPTABLE, responseBody.getResponseStatus());
	    }

	    @Test
	    @Order(6)
	    void testHandlerUserActivationException() {
	        String errorMessage = "Error activating user";
	        when(userActivationException.getMessage()).thenReturn(errorMessage);
	        ResponseEntity<ApiResponse> responseEntity = globalExceptionHandler.handlerUserActivationException(userActivationException);
	        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
	        ApiResponse responseBody = responseEntity.getBody();
	        assertEquals(true, responseBody.isResponseSuccess());
	        assertEquals(errorMessage, responseBody.getResponseMessage());
	        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseBody.getResponseStatus());
	    }

	    @Test
	    @Order(7)
	    void testHandlerUserAlreadyActiveException() {
	        String errorMessage = "User is already active";
	        when(userAlreadyActiveException.getMessage()).thenReturn(errorMessage);
	        ResponseEntity<ApiResponse> responseEntity = globalExceptionHandler.handlerUserAlreadyActiveException(userAlreadyActiveException);
	        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	        ApiResponse responseBody = responseEntity.getBody();
	        assertEquals(true, responseBody.isResponseSuccess());
	        assertEquals(errorMessage, responseBody.getResponseMessage());
	        assertEquals(HttpStatus.BAD_REQUEST, responseBody.getResponseStatus());
	    }
	    
	    @Test
	    @Order(8)
	    void testHandlerUserAlreadyInactiveException() {
	        String errorMessage = "User is already inactive";
	        when(userAlreadyInactiveException.getMessage()).thenReturn(errorMessage);
	        ResponseEntity<ApiResponse> responseEntity = globalExceptionHandler.handlerUserAlreadyInactiveException(userAlreadyInactiveException);
	        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	        ApiResponse responseBody = responseEntity.getBody();
	        assertEquals(true, responseBody.isResponseSuccess());
	        assertEquals(errorMessage, responseBody.getResponseMessage());
	        assertEquals(HttpStatus.BAD_REQUEST, responseBody.getResponseStatus());
	    }

	    @Test
	    @Order(9)
	     void testHandlerUserCreationException() {
	        // Arrange
	        String errorMessage = "Error creating user";
	        when(userCreationException.getMessage()).thenReturn(errorMessage);
	        ResponseEntity<ApiResponse> responseEntity = globalExceptionHandler.handlerUserCreationException(userCreationException);
	        assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
	        ApiResponse responseBody = responseEntity.getBody();
	        assertEquals(true, responseBody.isResponseSuccess());
	        assertEquals(errorMessage, responseBody.getResponseMessage());
	        assertEquals(HttpStatus.NOT_ACCEPTABLE, responseBody.getResponseStatus());
	    }

	    @Test
	    @Order(10)
	    void testHandlerUserDeactivationException() {
	        String errorMessage = "Error deactivating user";
	        when(userDeactivationException.getMessage()).thenReturn(errorMessage);
	        ResponseEntity<ApiResponse> responseEntity = globalExceptionHandler.handlerUserDeactivationException(userDeactivationException);
	        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	        ApiResponse responseBody = responseEntity.getBody();
	        assertEquals(true, responseBody.isResponseSuccess());
	        assertEquals(errorMessage, responseBody.getResponseMessage());
	        assertEquals(HttpStatus.BAD_REQUEST, responseBody.getResponseStatus());
	    }
	    
	    @Test
	    @Order(11)
	    void testHandlerUserDeletionException() {
	        String errorMessage = "Error deleting user";
	        when(userDeletionException.getMessage()).thenReturn(errorMessage);
	        ResponseEntity<ApiResponse> responseEntity = globalExceptionHandler.handlerUserDeletionException(userDeletionException);
	        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	        ApiResponse responseBody = responseEntity.getBody();
	        assertEquals(true, responseBody.isResponseSuccess());
	        assertEquals(errorMessage, responseBody.getResponseMessage());
	        assertEquals(HttpStatus.BAD_REQUEST, responseBody.getResponseStatus());
	    }

	    @Test
	    @Order(12)
	    void testHandlerUserFetchException() {
	        String errorMessage = "Error fetching user details";
	        when(userFetchException.getMessage()).thenReturn(errorMessage);
	        ResponseEntity<ApiResponse> responseEntity = globalExceptionHandler.handlerUserDeletionException(userFetchException);
	        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	        ApiResponse responseBody = responseEntity.getBody();
	        assertEquals(true, responseBody.isResponseSuccess());
	        assertEquals(errorMessage, responseBody.getResponseMessage());
	        assertEquals(HttpStatus.BAD_REQUEST, responseBody.getResponseStatus());
	    }

	    @Test
	    @Order(13)
	    void testHandlerUserUpdateException() {
	        // Arrange
	        String errorMessage = "Error updating user";
	        when(userUpdateException.getMessage()).thenReturn(errorMessage);
	        ResponseEntity<ApiResponse> responseEntity = globalExceptionHandler.handlerUserUpdateException(userUpdateException);
	        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
	        ApiResponse responseBody = responseEntity.getBody();
	        assertEquals(true, responseBody.isResponseSuccess());
	        assertEquals(errorMessage, responseBody.getResponseMessage());
	        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseBody.getResponseStatus());
	    }
	    @Test
	    @Order(14)
	     void testHandlerException() {
	        String errorMessage = "An error occurred: General error";
	        when(generalException.getMessage()).thenReturn("General error");
	        ResponseEntity<ApiResponse> responseEntity = globalExceptionHandler.handlerException(generalException);
	        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
	        ApiResponse responseBody = responseEntity.getBody();
	        assertEquals(false, responseBody.isResponseSuccess());
	        assertEquals(errorMessage, responseBody.getResponseMessage());
	        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseBody.getResponseStatus());
	    }
	    
	    @Test
	    @Order(15)
	    void testHandlerDuplicateEmailException() {
	    	String errorMessage = "Duplicate email found";
	        DuplicationException mockException = mock(DuplicationException.class);
	        when(mockException.getMessage()).thenReturn("Duplicate email found");
	        ResponseEntity<ApiResponse> responseEntity = globalExceptionHandler.handlerDuplicateEmailException(mockException);
	        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());   
	        ApiResponse responseBody = responseEntity.getBody();
	        assertEquals(true, responseBody.isResponseSuccess());
	        assertEquals(errorMessage, responseBody.getResponseMessage());
	        assertEquals(HttpStatus.CONFLICT, responseBody.getResponseStatus());
	    }
	}




