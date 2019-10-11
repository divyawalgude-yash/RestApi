package com.emp.controller;

import java.util.List;
import java.util.Optional;

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
import com.emp.model.EmployeeNotFoundException;
import com.emp.service.EmployeeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "SwaggerForEmployeeController", description = "REST Apis related to Employee Entity!!!!")
@RestController
@RequestMapping(value = "/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@ApiOperation(value = "Get list of Employee in the System ", response = Iterable.class, tags = "getEmployees")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 404, message = "not found!!!") })

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public List<Employee> getEmployeeList() {
		System.out.println("in get");
		List<Employee> emplist = employeeService.getEmployeeList();
		if (emplist.isEmpty()) {
			throw new EmployeeNotFoundException("Employees Not Found");
		} else
			return emplist;
	}

	@ApiOperation(value = "Get specific Employee in the System ", response = Employee.class, tags = "getEmployee")
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public Employee getEmployeeById(@PathVariable("id") long id) {

		Optional<Employee> emp = employeeService.getEmployeeById(id);
		System.out.println("*************in employee by id ********************" + emp.toString());
		if (emp.isPresent()) {
			return emp.get();
		} else
			throw new EmployeeNotFoundException("Employee Not Found");

	}

	@ApiOperation(value = "Add specific Employee in the System ", response = Employee.class, tags = "Add Employee")
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public Employee saveEmployee(@RequestBody Employee employee) {
		Employee saveEmp = employeeService.saveEmployee(employee);
		System.out.println("....post...");
		return saveEmp;
//		}
	}

//
	@ApiOperation(value = "update Employee in the System ", response = Employee.class, tags = "Update Employee")
	@PutMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public Employee updateEmployee(@PathVariable long id, @RequestBody Employee employee) {
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
			return emp.get();
		} else {
			throw new EmployeeNotFoundException("Employee Not Found");
		}
	}

	@ApiOperation(value = "Delete Specific Employee in the System ", response = Employee.class, tags = "Delete Employee")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable long id) {
		Optional<Employee> emp = employeeService.getEmployeeById(id);
		if (emp.isPresent()) {
			employeeService.deleteEmployee(emp.get());
			return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
		} else {
			throw new EmployeeNotFoundException("Employee Not Found");
		}
	}

}
