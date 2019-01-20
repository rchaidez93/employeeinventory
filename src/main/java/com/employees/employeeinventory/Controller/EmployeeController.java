package com.employees.employeeinventory.Controller;


import com.employees.employeeinventory.Model.Supervising;
import com.employees.employeeinventory.Model.Supervisor;
import com.employees.employeeinventory.Exception.InvalidEmployeeException;
import com.employees.employeeinventory.Exception.ResourceNotFoundException;
import com.employees.employeeinventory.Model.Employee;

import com.employees.employeeinventory.Utils.DateValidator;
import com.employees.employeeinventory.Utils.EmployeeValidations;
import com.employees.tables.records.EmployeePayRollRecord;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
public class EmployeeController {

    @Autowired private EmployeeService employeeService;

    private static Log logger = LogFactory.getLog(EmployeeController.class);

    //get all employee information
    @GetMapping("/get_all_employees")
    public String getAllEmployees(){

        Gson gsonBuilder = new GsonBuilder().create();
        List<Employee> initial_employee_list = employeeService.get_all_employees();
        String jsonFromJavaArray = gsonBuilder.toJson(initial_employee_list);

        return jsonFromJavaArray;
    }

    //add a new employee
    @PostMapping("/add_employee")
    public ResponseEntity<?> createEmployee(@Valid @RequestBody Employee employee){

        employeeService.add_employee(employee);

        return new ResponseEntity<>("Employee was added successfully!", HttpStatus.OK);
    }

    //get employee by id
    @GetMapping("/employee/{id}")
    public Employee getEmployeeById(@PathVariable(value = "id") Integer id){

        Employee employee = employeeService.findEmployeeById(id);

        return employee;
    }

    //update existing employee
    @PutMapping("/update_employee/{id}")
    public ResponseEntity<?> updateEmployeeById(@PathVariable(value = "id") Integer id,@Valid @RequestBody Employee employeeInfo){

        employeeService.updateEmployeeInfo(employeeInfo);

        return new ResponseEntity<>("Employee was updated successfully!", HttpStatus.OK);
    }

    //remove employee
    @DeleteMapping("/delete_employee/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable(value = "id") Integer id){

        employeeService.deleteEmployee(id);

        return new ResponseEntity<>("Employee deleted successfully",HttpStatus.OK);
    }


}
