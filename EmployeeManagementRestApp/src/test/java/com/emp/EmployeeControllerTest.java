package com.emp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import com.emp.controller.EmployeeController;
import com.emp.dao.EmployeeDao;
import com.emp.model.Employee;
import com.emp.model.EmployeeNotFoundException;
import com.emp.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(value = EmployeeController.class)
public class EmployeeControllerTest {

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		mockMvc = webAppContextSetup(this.wac).build();
	}

	@MockBean
	private EmployeeService employeeService;

	@MockBean
	private EmployeeDao employeeDao;

	@Test
	public void shouldReturnEmployeeListInJsonDataFormat() throws Exception {
		List<Employee> employeeList = new ArrayList<Employee>();
		employeeList.add(getDummyEmployee());

		when(employeeService.getEmployeeList()).thenReturn(employeeList);

		RequestBuilder requestBuilder = get("/employees/").accept(MediaType.APPLICATION_JSON_VALUE);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
		verify(employeeService).getEmployeeList();
	}
  
	@Test
	public void shouldReturnEmployeeListInXmlDataformat() throws Exception {
		List<Employee> employeeList = new ArrayList<Employee>();
		employeeList.add(getDummyEmployee());

		when(employeeService.getEmployeeList()).thenReturn(employeeList);

		RequestBuilder requestBuilder = get("/employees/").accept(MediaType.APPLICATION_XML_VALUE);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
		verify(employeeService).getEmployeeList();
	}

	@Test
	public void shouldReturnEmployeeDataByIdInJsonFormat() throws Exception {
		Optional<Employee> opEmp = Optional.ofNullable(getDummyEmployee());
		when(employeeService.getEmployeeById((long) 10)).thenReturn(opEmp);
		RequestBuilder requestBuilder = get("/employees/10").accept(MediaType.APPLICATION_JSON_VALUE);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
		verify(employeeService).getEmployeeById((long) 10);
	}

	@Test
	public void shouldReturnEmployeeDataByIdInXmlFormat() throws Exception {
		Optional<Employee> opEmp = Optional.ofNullable(getDummyEmployee());

		when(employeeService.getEmployeeById((long) 10)).thenReturn(opEmp);

		RequestBuilder requestBuilder = get("/employees/10").accept(MediaType.APPLICATION_XML_VALUE);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
		verify(employeeService).getEmployeeById((long) 10);
	}

	@Test
	public void shouldReturnCheckPostMethod() throws Exception {

		Employee employee = new Employee("divya", "software Engg", 40000);

		when(employeeService.saveEmployee(employee)).thenReturn(getDummyEmployee());

		mockMvc.perform(post("/employees/").contentType(MediaType.APPLICATION_JSON).content(asJsonString(employee)))
				.andExpect(status().isOk());

		verify(employeeService).saveEmployee(employee);

	}

	@Test
	public void shouldReturnOkIfDataIsDelete() throws Exception {

		Optional<Employee> opEmp = Optional.ofNullable(getDummyEmployee());

		when(employeeService.getEmployeeById((long) 14)).thenReturn(opEmp);
		doNothing().when(employeeService).deleteEmployee(getDummyEmployee());

		mockMvc.perform(delete("/employees/{id}", 14)).andExpect(status().isAccepted());

		verify(employeeService).deleteEmployee(getDummyEmployee());
	}

	@Test
	public void ShouldReturnOkIfEmployeeDataIsupdate() throws Exception {

		Employee employee = new Employee("divya", "software Engg", 40000);

		Optional<Employee> opEmp = Optional.ofNullable(employee);
		when(employeeService.updateEmployee(employee)).thenReturn(getDummyEmployee());
		when(employeeService.getEmployeeById((long) 12)).thenReturn(opEmp);
		mockMvc.perform(put("/employees/12").contentType(MediaType.APPLICATION_JSON).content(asJsonString(employee)))
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.name").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.designation").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.salary").exists());

		verify(employeeService).updateEmployee(employee);
	}

	@Test
	public void shouldReturnThrowEmployeeNotFoundExceptionDataById() throws Exception {

		RequestBuilder requestBuilder = get("/employees/999");
		mockMvc.perform(requestBuilder).andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value("Not Found"))
				.andExpect(jsonPath("$.message").value("Employee Not Found"));

		verify(employeeService).getEmployeeById((long) 999);
	}

	@Test
	public void shouldReturnThrowEmployeeNotFoundExceptionIfEmployeeListIsEmpty() throws Exception {

		List<Employee> emplist = new ArrayList<Employee>();
		when(employeeService.getEmployeeList()).thenReturn(emplist);
		RequestBuilder requestBuilder = get("/employees/");
		mockMvc.perform(requestBuilder).andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value("Not Found"))
				.andExpect(jsonPath("$.message").value("Employees Not Found"));

		verify(employeeService).getEmployeeList();
	}

	@Test
	public void shouldReturnThrowEmployeeNotFoundExceptionIfDataIsNotUpdate() throws Exception {

		Employee employee = new Employee("divya", "software Engg", 40000);

		mockMvc.perform(put("/employees/6000").contentType(MediaType.APPLICATION_JSON).content(asJsonString(employee)))
				.andExpect(status().isNotFound()).andExpect(jsonPath("$.status").value("Not Found"))
				.andExpect(jsonPath("$.message").value("Employee Not Found"));
	}

	@Test
	public void shouldReturnThrowEmployeeNotFoundExceptionIfDataIsNotDelete() throws Exception {

		doNothing().when(employeeService).deleteEmployee(getDummyEmployee());

		mockMvc.perform(delete("/employees/{id}", 7000)).andExpect(status().isNotFound());
	}

	@Test
	public void shouldReturnThrowBadRequestException() throws Exception {

		doNothing().when(employeeService).deleteEmployee(getDummyEmployee());

		mockMvc.perform(delete("/employees/{id}", "gfdfgd")).andExpect(status().isBadRequest());
	}


	
	public Employee getDummyEmployee() {
		Employee employee = new Employee();
		employee.setId((long) 10);
		employee.setName("divya");
		employee.setDesignation("software");
		employee.setSalary(400000);
		return employee;
	}

	
	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
