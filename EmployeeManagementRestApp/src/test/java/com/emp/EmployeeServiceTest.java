package com.emp;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.emp.dao.EmployeeDao;
import com.emp.model.Employee;
import com.emp.service.EmployeeService;
import com.emp.service.EmployeeServiceImpl;

@RunWith(SpringRunner.class)
public class EmployeeServiceTest {

	@Mock
	EmployeeDao employeeDao;
	
	@InjectMocks
	EmployeeServiceImpl employeeService;
	
	
	@Test
	public void ShouldReturnCheckEmployeeList()
	{
		List<Employee>empList=new ArrayList<Employee>();
		empList.add(new Employee((long)2,"divya","software Engineer",40000));
		when(employeeDao.findAll()).thenReturn(empList);
		employeeService.getEmployeeList();
		verify(employeeDao).findAll();
	}
	
	@Test
	public void ShouldReturnCheckEmployeeGetById()
	{	
		Optional<Employee>emp=Optional.ofNullable(new Employee((long)2,"divya","software Engineer",40000));
		when(employeeDao.findById((long)2)).thenReturn(emp);
		employeeService.getEmployeeById((long)2);
		verify(employeeDao).findById((long)2);
	}
	
	
	
	@Test
	public void ShouldReturnCheckSaveOprationOfEmployee()
	{
		Employee emp=new Employee((long)2,"divya","software Engineer",40000);
		when(employeeDao.save(emp)).thenReturn(emp);
		employeeService.saveEmployee(emp);
		verify(employeeDao).save(emp);
	}
	
	@Test
	public void ShouldReturnCheckUpdateOprationOfEmployee()
	{	
		Employee emp=new Employee((long)2,"divya","software Engineer",40000);
		when(employeeDao.save(emp)).thenReturn(emp);
		employeeService.updateEmployee(emp);
		verify(employeeDao).save(emp);
	}
	
	@Test
	public void ShouldReturnCheckDeleteOprationOfEmployee()
	{	
		Employee emp=new Employee((long)2,"divya","software Engineer",40000);
		doNothing().when(employeeDao).delete(emp);
		employeeService.deleteEmployee(emp);
		verify(employeeDao).delete(emp);
	}
	
	
}
