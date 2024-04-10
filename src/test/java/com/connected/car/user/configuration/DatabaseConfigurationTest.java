package com.connected.car.user.configuration;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.connected.car.user.exceptions.custom.DatabaseConnectionException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@SpringBootTest
@ComponentScan(basePackages = "com.connected.car.user.configuration")
 class DatabaseConfigurationTest {
	
	@Value("${spring.datasource.url}")
	private String dbUrl;

	@Value("${spring.datasource.username}")
	private String username;

	@Value("${spring.datasource.password}")
	private String password;
	   @Test
	     void testDataSource() {
		 DatabaseConfig data = new DatabaseConfig();
	        String validDbUrl =  dbUrl;
	        String validUsername = username;
	        String validPassword = password;
	        data.setDbUrl(validDbUrl);
	        data.setUsername(validUsername);
	        data.setPassword(validPassword);
	        DataSource dataSource = data.dataSource();
	        assertNotNull(dataSource);
	    }
	 
	  
	   //Input  suppose if u give wrong credentials  which is not present in application properties file 
	   @Test
	    void testDataSourceWithInvalidCredentials() {
	        DatabaseConfig data = new DatabaseConfig();
	        String invalidDbUrl = "jdbc:mysql://localhost:3306/user2";
	        String validUsername = "navya";
	        String validPassword = "1234577";
	        data.setDbUrl(invalidDbUrl); 
	        data.setUsername(validUsername);
	        data.setPassword(validPassword);
	        try {
	            DataSource dataSource = data.dataSource();
	            fail("Expected DatabaseConnectionException, but no exception was thrown");
	        } catch (DatabaseConnectionException e) {
	           
	        }
	    }
	}

