package com.employees.employeeinventory.Controller;


import com.employees.employeeinventory.Repository.EmployeeRepository;
import com.employees.employeeinventory.Exception.InvalidEmployeeException;
import com.employees.employeeinventory.Exception.ResourceNotFoundException;
import com.employees.employeeinventory.Model.Employee;
import com.employees.employeeinventory.Utils.DateValidator;
import com.employees.employeeinventory.Utils.EmployeeValidations;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<?> createEmployee(@Valid @RequestBody Employee employee){
        EmployeeValidations employeeValidations = new EmployeeValidations();
        //Validate new employee information
        employeeValidations.checkEmployeeInfo(employeeRepository.findAll(),employee);
        employeeRepository.save(employee);
        return new ResponseEntity<>("Employee was added successfully!", HttpStatus.OK);
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
    public ResponseEntity<?> updateEmployeeById(@PathVariable(value = "id") Integer id,@Valid @RequestBody Employee employeeInfo){

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(id,"Sorry the employee id : "+ id +" was not found"));

        employee.setFirst_name(employeeInfo.getFirst_name());
        employee.setLast_name(employeeInfo.getLast_name());
        employee.setDob(employeeInfo.getDob());
        employee.setSalary(employeeInfo.getSalary());
        employee.setSsn(employeeInfo.getSsn());
        employee.setStart_date(employeeInfo.getStart_date());

        Employee updateEmployee = employeeRepository.save(employee);


        return new ResponseEntity<>("Employee was updated successfully!", HttpStatus.OK);
    }

    //remove employee
    @DeleteMapping("/delete_employee/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable(value = "id") Integer id){

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(()->  new ResourceNotFoundException(id,"Sorry the employee id : "+ id +" was not found"));

        employeeRepository.delete(employee);

        return new ResponseEntity<>("Employee deleted successfully",HttpStatus.OK);
    }


}
