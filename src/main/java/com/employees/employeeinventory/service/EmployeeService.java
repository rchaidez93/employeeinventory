package com.employees.employeeinventory.service;

import com.employees.employeeinventory.model.Employee;

import java.util.List;

public class EmployeeService {


    public boolean checkEmployeeSSN(List<Employee> employee, Integer SSN){

        for(Employee employee1 : employee){
            if(SSN == employee1.getSsn()){
                return false;
            }
        }

        return true;
    }
}
