package com.employees.employeeinventory.Utils;

import com.employees.employeeinventory.Controller.EmployeeController;
import com.employees.employeeinventory.Exception.InvalidEmployeeException;
import com.employees.employeeinventory.Model.Employee;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

public class EmployeeValidations {

    private static Log logger = LogFactory.getLog(EmployeeController.class);

    public void checkEmployeeSSN(List<Employee> employee, Integer SSN){
        Integer ssnSize = SSN.toString().length();

        if(SSN == 0){
            throw new InvalidEmployeeException(employee.hashCode(),"SSN must not be null");
        }
        else if(ssnSize < 9 || ssnSize > 9){
            throw new InvalidEmployeeException(employee.hashCode(),"Invalid SSN");
        }

        for(Employee employee1 : employee){

            if(SSN == employee1.getSsn()) {
                throw new InvalidEmployeeException(employee1.getId(),"SSN already exists");
            }

        }
    }
}
