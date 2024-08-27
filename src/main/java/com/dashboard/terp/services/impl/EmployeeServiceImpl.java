package com.dashboard.terp.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dashboard.terp.entities.Employees;
import com.dashboard.terp.repositories.IEmployeeRepo;
import com.dashboard.terp.services.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	IEmployeeRepo empRepo;
	
	@Override
	public Employees save(Employees empObj) {
		Employees savedEmp = empRepo.save(empObj);
		return savedEmp;
	}

}
