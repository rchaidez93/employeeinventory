package com.employees.employeeinventory.Utils;

import com.employees.employeeinventory.Controller.EmployeeController;
import com.employees.employeeinventory.Exception.InvalidEmployeeException;
import com.employees.employeeinventory.Model.Employee;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

public class EmployeeValidations {

    private static Log logger = LogFactory.getLog(EmployeeController.class);

    public void checkEmployeeInfo(List<Employee> employee, Employee employeeInfo){
        Integer employeeSSN = employeeInfo.getSsn();
        Integer ssnSize = employeeSSN.toString().length();

        if(employeeInfo.getSsn() == 0){
            throw new InvalidEmployeeException(employeeInfo.getId(),"SSN must not be null");
        }
        else if(ssnSize < 9 || ssnSize > 9){
            throw new InvalidEmployeeException(employeeInfo.getId(),"Invalid SSN");
        }

        for(Employee employee1 : employee){

            if(employeeInfo.getSsn() == employee1.getSsn()) {
                throw new InvalidEmployeeException(employee1.getId(),"SSN already exists");
            }

        }
    }

    public void checkEmployeeIdExist(List<Employee> employee, Integer employee_id){

    }
}
