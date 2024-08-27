package com.dashboard.terp.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dashboard.terp.security.JwtUtil;
import com.dashboard.terp.services.CustomSecurityUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private CustomSecurityUserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authHeader = request.getHeader("Authorization");
		
		String email=null,token=null;
		if(authHeader!=null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);
			email = jwtUtil.extractEmailId(token);
			
		}
		System.out.println(authHeader+" " + email+ "  "+token);

		if(email!=null  && SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(email);
			if(jwtUtil.validateToken(token, userDetails)) {
				System.out.println("token validated ");
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails	,null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}else {
				System.out.println("token not  validated ");
			}
		}
		
		filterChain.doFilter(request, response);
		

		// TODO Auto-generated method stub
		
	}

}
