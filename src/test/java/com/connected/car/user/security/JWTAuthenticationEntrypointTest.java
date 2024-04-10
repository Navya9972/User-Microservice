package com.connected.car.user.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.AuthenticationException;


import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class JWTAuthenticationEntrypointTest {

    @Mock
   private  HttpServletRequest request;
    @Mock
    private HttpServletResponse response;

    @Mock
    AuthenticationException authException;

    @InjectMocks
    private JWTAutheticationEntrypoint authenticationEntryPoint;

    @Test
    public void commenceHandlesJWTException() throws IOException, ServletException {

        PrintWriter mockWriter = mock(PrintWriter.class);


       Mockito.when(response.getWriter()).thenReturn(mockWriter);
        authenticationEntryPoint.commence(request, response, authException);
        Mockito.verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        Mockito.verify(response).setContentType("application/json");
        Mockito.verify(mockWriter).write("{\"error\": \"Unauthorized\", \"message\": \"Something Token Issuse\"}");

    }
}
 