package com.dashboard.terp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dashboard.terp.dto.LoginDTO;
import com.dashboard.terp.entities.Employees;
import com.dashboard.terp.security.JwtUtil;
import com.dashboard.terp.services.EmployeeService;

@RestController
@RequestMapping("/api/employee")
public class EmployeesControllers {
	
	@Autowired
	JwtUtil jwtUtil;

	@Autowired
	EmployeeService empService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public String getAllEmployees() {
		return "test";
	}
	
	@GetMapping("/without")
	public String withoutAnySecurity() {
		return "Acces without any security";
	}
	
	@PostMapping
	public Employees employeeLogin(@RequestBody Employees emp) {
		emp.setPassword(passwordEncoder.encode(emp.getPassword()));
		return empService.save(emp);
	}
	
	@PostMapping("/login") 
	public String login(@RequestBody LoginDTO loginDetails) {
		Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(loginDetails.getEmail(), loginDetails.getPassword()));
		if(auth.isAuthenticated()) {
			return jwtUtil.generateToken(1, loginDetails.getEmail(), "ADMIN");
		}else {
			return "Error";
		}
	}
	
}
