package com.dashboard.terp.entities;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;



@Entity
@Table(name="EMPLOYEES")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employees {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable=false)
	private long empid;
	
	@Column(nullable=false)
	private String name;
	
	@Column(unique=true,length=100,nullable=false)
	private String email;
	
	private String phone;
	
	@Column(nullable=false)
	private String password;
	
	
	private String role;
	private String passwordResetToken;
	private LocalDateTime passwordResetTokenExpirty;
	private LocalDateTime lassPasswordChangedDt;
	
	private boolean status;
	
	@CreationTimestamp
	@Column(updatable=false)
	private LocalDateTime createdAt;
	
	private String createdBy;
	
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	private String updatedby;
	

	

}
