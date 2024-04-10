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
import org.springframework.security.access.AccessDeniedException;

import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class CustomAccessDeniedHandlerTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;

    @Mock
    private AccessDeniedException accessDeniedException;

    @InjectMocks
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Test
    public void handleTest() throws IOException, ServletException {

        PrintWriter mockWriter = mock(PrintWriter.class);
        Mockito.when(response.getWriter()).thenReturn(mockWriter);
        customAccessDeniedHandler.handle(request, response, accessDeniedException);

        Mockito.verify(response).setStatus(HttpServletResponse.SC_FORBIDDEN);
        Mockito.verify(response).setContentType("application/json");
        Mockito.verify(mockWriter).write("{\"error\": \"Forbidden\", \"message\": \"Access denied for This role\"}");

    }
}
 