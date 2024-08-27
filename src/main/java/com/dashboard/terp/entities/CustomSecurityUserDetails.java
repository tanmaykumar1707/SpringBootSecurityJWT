package com.dashboard.terp.entities;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


public class CustomSecurityUserDetails implements UserDetails {

	
	private String email;
	private String password;
	private boolean isEnabled;
	private List<GrantedAuthority> authority;
	
	public CustomSecurityUserDetails(Employees emp) {
		 this.email = emp.getEmail();
		 this.password = emp.getPassword();
		 this.isEnabled = true;
		 this.authority = Arrays.stream(emp.getRole().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authority;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}
	
	@Override
	public boolean isEnabled() {
		return isEnabled;
	}


	@Override
	public boolean isAccountNonExpired() {
		return true;
	}


	@Override
	public boolean isAccountNonLocked() {
		return true;
	}


	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
}
