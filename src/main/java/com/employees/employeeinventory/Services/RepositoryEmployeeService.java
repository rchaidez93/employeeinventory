package com.employees.employeeinventory.Services;

import com.employees.employeeinventory.Controller.EmployeeController;
import com.employees.employeeinventory.Model.Employee;
import com.employees.employeeinventory.Repository.EmployeeRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RepositoryEmployeeService implements EmployeeServices {

    @Resource
    private EmployeeRepository employeeRepository;
    private static Log logger = LogFactory.getLog(RepositoryEmployeeService.class);

    @Override
    public List<Employee> getEmployeeSupervisor(Integer supervisor_id) {

        logger.info("In here");

        return findEmployeeSupervisorById(supervisor_id);
    }


    @Override
    public List<Employee> getEmployeeSupervising(Integer supervisor_id) {
        return null;
    }

    private List<Employee> findEmployeeSupervisorById(Integer supervisor_id) {
        List<Employee> employees;

        employees = employeeRepository.findSupervisorById(supervisor_id);

        return employees;
    }
}
