package com.dashboard.terp.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.dashboard.terp.entities.CustomSecurityUserDetails;
import com.dashboard.terp.entities.Employees;
import com.dashboard.terp.repositories.IEmployeeRepo;

@Service
public class CustomSecurityUserDetailsService implements UserDetailsService{

	@Autowired
	private IEmployeeRepo employeeRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Employees> emp = employeeRepository.findByEmail(username);
		
		return  emp.map(CustomSecurityUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("User not found "+username) );
	}

}
