package com.emp;

import org.junit.Test;

import com.emp.model.Employee;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class EmployeeTest {

	@Test
	public void shouldTestEqualAndHashCodeMethod()
	{
		EqualsVerifier.forClass(Employee.class).suppress(Warning.NONFINAL_FIELDS).usingGetClass().verify();
	}
}
