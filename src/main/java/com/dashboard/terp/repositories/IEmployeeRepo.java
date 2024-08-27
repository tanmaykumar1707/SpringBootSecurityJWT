package com.dashboard.terp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dashboard.terp.entities.Employees;

@Repository
public interface IEmployeeRepo extends JpaRepository<Employees, Long>{
	
	   Optional<Employees> findByEmail(String email);
	
	
}
