package com.emp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestBody;

import com.emp.model.Employee;

public interface EmployeeService {

	public Employee saveEmployee(Employee employee);
	public Optional<Employee> getEmployeeById(Long id);
	public List<Employee> getEmployeeList();
	public Employee updateEmployee(Employee employee);
	public void deleteEmployee(Employee employee);
	
}
