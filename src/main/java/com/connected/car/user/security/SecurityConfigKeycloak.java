package com.connected.car.user.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import com.connected.car.user.constants.ResponseMessage;

@Configuration
@EnableWebSecurity
public class SecurityConfigKeycloak {
	

    
    public SecurityConfigKeycloak(JwtAuthConverter jwtAuthConverter, JWTAutheticationEntrypoint entryPoint,
			CustomAccessDeniedHandler customaccessdenied) {
		super();
		this.jwtAuthConverter = jwtAuthConverter;
		this.entryPoint = entryPoint;
		this.customaccessdenied = customaccessdenied;
	}


	public JwtAuthConverter jwtAuthConverter;
   
    private JWTAutheticationEntrypoint entryPoint;
   
    private CustomAccessDeniedHandler customaccessdenied;


  @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
      http.csrf(csrf->csrf.disable());
      http.authorizeHttpRequests(authorize ->
          authorize
                  .requestMatchers("/swagger-ui/**","/v3/api-docs/**").permitAll()
                  .requestMatchers("/v1/api/users/create").permitAll()
                  .requestMatchers("v1/api/users/update/*").hasRole(ResponseMessage.ADMIN)
                  .requestMatchers("v1/api/users/delete/*").hasRole(ResponseMessage.ADMIN)
                  .requestMatchers("v1/api/users/getAllUsers").hasRole(ResponseMessage.ADMIN)
                  .requestMatchers("v1/api/users/getUser/*").hasRole(ResponseMessage.USER)
                  .requestMatchers("v1/api/users/getStatus/*").hasRole(ResponseMessage.USER)
                  .requestMatchers("v1/api/users/InactiveStatus/*").hasRole(ResponseMessage.USER)
                  .requestMatchers("v1/api/users/getAllActiveUsers").hasRole(ResponseMessage.ADMIN)
                  .requestMatchers("v1/api/users/getAllInactiveUsers").hasRole(ResponseMessage.ADMIN)
                  .anyRequest().authenticated()
      );

      http.oauth2ResourceServer(t ->
          t.jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwtAuthConverter))
      );

      http.sessionManagement(
              t -> t.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      );
      http.exceptionHandling(exception->exception.authenticationEntryPoint(entryPoint))
      .exceptionHandling(exception1->exception1.accessDeniedHandler(customaccessdenied))
;
      return http.build();
      

  }
}
