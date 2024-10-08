package com.dashboard.terp.config;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
//			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//			response.setContentType("applicaiton/json");
//			response.getWriter().write("{\"message\" : \"unAuthrozidAccess "+authException.getMessage()+" }");
//			
//			

			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);

	        final Map<String, Object> body = new HashMap<>();
	        body.put("code", HttpServletResponse.SC_FORBIDDEN);
	        body.put("payload", "You don't have required role to perform this action.");

	        final ObjectMapper mapper = new ObjectMapper();
	        mapper.writeValue(response.getOutputStream(), body);
	}

}


