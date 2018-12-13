package com.employees.employeeinventory.Services;

import com.employees.employeeinventory.Model.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

public interface EmployeeServices {

    public List<Employee> getEmployeeSupervisor(Integer supervisor_id);

    public List<Employee> getEmployeeSupervising(Integer supervisor_id);
}
