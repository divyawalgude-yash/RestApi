package com.emp.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emp.model.Employee;
import com.emp.model.EmployeeList;
import com.emp.model.EmployeeNotFoundException;
import com.emp.service.EmployeeServiceImpl;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
	EmployeeServiceImpl employeeService;

	@GetMapping(value = "/all", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public EmployeeList getEmployeeList() {
		System.out.println("in get");
		Optional<List<Employee>> emplist = Optional.ofNullable(employeeService.getEmployeeList());
		EmployeeList employees = new EmployeeList();

		if (emplist.isPresent()) {
			employees.setEmployees(emplist.get());
			return employees;
		} else
			throw new EmployeeNotFoundException("Employees Data Not Found");
	}

	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public Employee getEmployeeById(@PathVariable("id") long id) {
		Optional<Employee> emp = employeeService.getEmployeeById(id);
		if (emp.isPresent()) {
//			Resource<Employee> resource =new Resource<Employee>(emp.get());
//		    ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getEmployeeList());
//		    resource.add(linkTo.withRel("all-Employees"));
			return emp.get();
		} else
			throw new EmployeeNotFoundException("Employee Not Found");
	}

	@PostMapping(value = "/")
	public ResponseEntity<Employee> saveEmployee(@Valid @RequestBody Employee employee) {
		Employee saveEmp = employeeService.saveEmployee(employee);
		System.out.println("....post...");
//		Optional<Employee> optionalEmployee = Optional.ofNullable(saveEmp);
//		if (optionalEmployee.isPresent()) {

		return new ResponseEntity<Employee>(saveEmp, HttpStatus.CREATED);
//		}
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Employee> update(@PathVariable long id, @RequestBody Employee employee) {
		Optional<Employee> emp = employeeService.getEmployeeById(id);
		System.out.println(emp.toString());
		if (emp.isPresent()) {

			Optional<String> designation = Optional.ofNullable(employee.getDesignation());
			Optional<Integer> salary = Optional.ofNullable(employee.getSalary());
			Optional<String> name = Optional.ofNullable(employee.getName());

			if (name.isPresent())
				emp.get().setName(employee.getName());
			if (salary.isPresent())
				emp.get().setSalary(employee.getSalary());
			if (designation.isPresent())
				emp.get().setDesignation(employee.getDesignation());

			employeeService.updateEmployee(emp.get());
			return ResponseEntity.ok(emp.get());
		} else {
			throw new EmployeeNotFoundException("Employee Not Found");
		}
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> deleteEmployee(@PathVariable long id) {
		Optional<Employee> emp = employeeService.getEmployeeById(id);
		if (emp.isPresent()) {
			employeeService.deleteEmployee(emp.get());
			return ResponseEntity.noContent().build();
		} else {
			throw new EmployeeNotFoundException("Employee Not Found");
		}
	}
}
