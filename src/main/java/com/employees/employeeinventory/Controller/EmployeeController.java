package com.employees.employeeinventory.Controller;


import com.employees.employeeinventory.Repository.EmployeeRepository;
import com.employees.employeeinventory.exception.InvalidEmployeeException;
import com.employees.employeeinventory.exception.ResourceNotFoundException;
import com.employees.employeeinventory.model.Employee;
import com.employees.employeeinventory.service.EmployeeService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.util.List;


@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired private EmployeeRepository employeeRepository;

    private static Log logger = LogFactory.getLog(EmployeeController.class);

    //get all employee information
    @GetMapping("/get_all_employees")
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    //add a new employee
    @PostMapping("/add_employee")
    public Employee createEmployee(@Valid @RequestBody Employee employee){

        //Make sure someone is not adding new user with ssn that already exists
        EmployeeService employeeService = new EmployeeService();
        boolean uniqueEmployee = employeeService.checkEmployeeSSN(employeeRepository.findAll(),employee.getSsn());
        if(!uniqueEmployee)
            throw new InvalidEmployeeException(employee.getId(),"SSN already exists");

        return employeeRepository.save(employee);
    }

    //get employee by id
    @GetMapping("/employee/{id}")
    public Employee getEmployeeById(@PathVariable(value = "id") Integer id){
        logger.info("in here");
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id,"Sorry the employee id : "+ id +" was not found"));
    }

    //update existing employee
    @PutMapping("/update_employee/{id}")
    public Employee updateEmployeeById(@PathVariable(value = "id") Integer id,@Valid @RequestBody Employee employeeInfo){

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(id,"Sorry the employee id : "+ id +" was not found"));

        employee.setFirst_name(employeeInfo.getFirst_name());
        employee.setLast_name(employeeInfo.getLast_name());
        employee.setDob(employeeInfo.getDob());
        employee.setSalary(employeeInfo.getSalary());
        employee.setSsn(employeeInfo.getSsn());
        employee.setStart_date(employeeInfo.getStart_date());

        Employee updateEmployee = employeeRepository.save(employee);

        return updateEmployee;
    }

    //remove employee
    @DeleteMapping("/delete_employee/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable(value = "id") Integer id){

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(()->  new ResourceNotFoundException(id,"Sorry the employee id : "+ id +" was not found"));

        employeeRepository.delete(employee);

        return ResponseEntity.ok().build();
    }


}
