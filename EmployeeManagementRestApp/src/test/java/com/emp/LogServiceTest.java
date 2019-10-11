package com.emp;


import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.test.context.junit4.SpringRunner;

import com.emp.aspect.LoggingAspect;
import com.emp.dao.EmployeeDao;
import com.emp.model.Employee;
import com.emp.model.EmployeeNotFoundException;
import com.emp.service.EmployeeService;
import com.emp.service.EmployeeServiceImpl;


@RunWith(SpringRunner.class)
public class LogServiceTest {
	@Mock
	EmployeeDao employeeRepository;

	@InjectMocks
	EmployeeServiceImpl employeeService;

	@Test
	public void AOPTest() {
		Employee emp = new Employee((long)1, "ABC", "IT", 50000);
		when(employeeRepository.findById((long)1)).thenReturn(Optional.of(emp));

		AspectJProxyFactory proxyFactory = new AspectJProxyFactory(employeeService);
		proxyFactory.addAspect(LoggingAspect.class);
		EmployeeService proxy = proxyFactory.getProxy();
		proxy.getEmployeeById(emp.getId());
	}

	@Test(expected = EmployeeNotFoundException.class)
	public void AOPExceptionTest() {
		Employee emp = new Employee((long)1000, "ABC", "IT", 50000);
		when(employeeRepository.findById((long)1000)).thenThrow(EmployeeNotFoundException.class);

		AspectJProxyFactory proxyFactory = new AspectJProxyFactory(employeeService);
		proxyFactory.addAspect(LoggingAspect.class);
		EmployeeService proxy = proxyFactory.getProxy();
		proxy.getEmployeeById(emp.getId());
	}

	@Test
	public void pointcutTest() {
		LoggingAspect logService = new LoggingAspect();
		logService.applicationPoint();
	}
}
