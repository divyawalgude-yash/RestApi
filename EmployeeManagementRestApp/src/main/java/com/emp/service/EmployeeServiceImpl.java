package com.emp.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emp.dao.EmployeeDao;
import com.emp.model.Employee;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeDao employeeDao;

	@Override
	public Employee saveEmployee(Employee employee) {
		return employeeDao.save(employee);
	}

	@Override
	public Optional<Employee> getEmployeeById(Long id) {
		Optional<Employee> employee = employeeDao.findById(id);
		return employee;
	}

	@Override
	public List<Employee> getEmployeeList() {
		List<Employee> emplist = employeeDao.findAll();
		return emplist;
	}

	@Override
	public Employee updateEmployee(Employee employee) {

		Employee resultemp = employeeDao.save(employee);
		return resultemp;
	}

	@Override
	public void deleteEmployee(Employee employee) {
		 employeeDao.delete(employee);
	}

}
