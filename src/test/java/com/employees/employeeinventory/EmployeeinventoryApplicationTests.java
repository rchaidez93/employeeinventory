package com.employees.employeeinventory;

import com.employees.employeeinventory.Controller.EmployeeService;
import com.employees.employeeinventory.Exception.InvalidEmployeeException;
import com.employees.employeeinventory.Model.Employee;
import com.employees.tables.records.EmployeePayRollRecord;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeinventoryApplicationTests {

	@Autowired
	private EmployeeService employeeTest;

	@Test
	public void contextLoads() {

		List<EmployeePayRollRecord> employees = employeeTest.searchByName("amber");
		assertNotNull(employees);
		assertTrue(!employees.isEmpty());

		assertEquals("amber",employees.get(0).getFirstName());

		for(EmployeePayRollRecord employeePayRollRecord : employees){
			System.err.println(employeePayRollRecord);
		}
	}


	@Test
	public void get_all_employees() {
		System.out.println(employeeTest.get_all_employees());
	}

	@Test(expected = InvalidEmployeeException.class)
	public void addEmployeeWrongSSN(){
		Employee employee = new Employee();
		employee.setFirst_name("unit");
		employee.setLast_name("test");
		employee.setSalary(60000);
		employee.setSsn(2322);
		employee.setStart_date("1/12/2019");
		employee.setDob("05/03/1993");
		employee.setSupervisor(0);
		employee.setSupervisor_id(14);

		employeeTest.add_employee(employee);

	}

	@Test(expected = InvalidEmployeeException.class)
	public void add_new_employee(){
		Employee employee = new Employee();
		employee.setFirst_name("unit");
		employee.setLast_name("test");
		employee.setSalary(60000);
		employee.setSsn(213323456);
		employee.setStart_date("1/12/2019");
		employee.setDob("05/03/1993");
		employee.setSupervisor(0);
		employee.setSupervisor_id(14);

		employeeTest.add_employee(employee);

	}

	@Test
	public void findEmployeeById(){
		System.out.println(employeeTest.findEmployeeById(26));
	}





}
