package com.employees.employeeinventory.Controller;


import com.employees.employeeinventory.Model.Supervising;
import com.employees.employeeinventory.Model.Supervisor;
import com.employees.employeeinventory.Repository.EmployeeRepository;
import com.employees.employeeinventory.Exception.InvalidEmployeeException;
import com.employees.employeeinventory.Exception.ResourceNotFoundException;
import com.employees.employeeinventory.Model.Employee;
import com.employees.employeeinventory.Services.EmployeeServices;
import com.employees.employeeinventory.Utils.DateValidator;
import com.employees.employeeinventory.Utils.EmployeeValidations;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired private EmployeeRepository employeeRepository;
    @Autowired private EmployeeServices employeeServices;

    private static Log logger = LogFactory.getLog(EmployeeController.class);

    //get all employee information
    @GetMapping("/get_all_employees")
    public String getAllEmployees(){

        Gson gsonBuilder = new GsonBuilder().create();
        List<Employee> initial_employee_list = employeeRepository.findAll();
        List<Employee> unmodifiableEmployeeList = Collections.unmodifiableList(initial_employee_list);


        initial_employee_list.forEach(employee -> {
            List<Supervising> employeeSupervising = new ArrayList<>();
            List<Supervisor> employeeSupervisor = new ArrayList<>();
            if(employee.getSupervisor() == 1){
                //get supervised employees
                List<Employee> filtered_supervised_employees = unmodifiableEmployeeList
                        .stream()
                        .filter(employee1 -> employee1.getSupervisor_id() == employee.getSupervisor_id() && employee1.getSupervisor() == 0)
                        .collect(Collectors.toList());

                for(Employee employee1 : filtered_supervised_employees){
                    employeeSupervising.add(new Supervising(employee1.getId(),employee1.getFirst_name(),employee1.getLast_name(),employee1.getSupervisor(),employee.getSupervisor_id()));
                }

                employee.setSupervising(employeeSupervising);

            }
            else{
                //get employee supervisor
                List<Employee> filtered_employee_supervisor = unmodifiableEmployeeList
                        .stream()
                        .filter(employee2 -> employee2.getSupervisor_id() == employee.getSupervisor_id() && employee2.getSupervisor() == 1)
                        .collect(Collectors.toList());

                for(Employee employee1 : filtered_employee_supervisor){
                    employeeSupervisor.add(new Supervisor(employee1.getId(),employee1.getFirst_name(),employee1.getLast_name(),employee1.getSupervisor(),employee.getSupervisor_id()));
                }

                employee.setEmployee_supervisor(employeeSupervisor);

            }


        });

        String jsonFromJavaArray = gsonBuilder.toJson(initial_employee_list);

        return jsonFromJavaArray;
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
    public List<Employee> getEmployeeById(@PathVariable(value = "id") Integer id){


        Optional<Employee> employee = employeeRepository.findById(id);
        employee.orElseThrow(()-> new ResourceNotFoundException(id,"Sorry the employee id : "+ id +" was not found"));

        List<Employee> employee_supervisor;

        List<Employee> employee_supervising;

        return employeeServices.getEmployeeSupervisor(0);
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
