package com.employees.employeeinventory.Controller;


import com.employees.employeeinventory.Model.Employee;
import com.employees.employeeinventory.Utils.EmployeeValidations;
import com.employees.tables.EmployeePayRoll;
import com.employees.tables.records.EmployeePayRollRecord;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jooq.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.employees.tables.EmployeePayRoll.EMPLOYEE_PAY_ROLL;

@Component
public class EmployeeService {
    @Autowired
    private DSLContext dslContext;
    private static Log logger = LogFactory.getLog(EmployeeService.class);

    EmployeeValidations employeeValidations = new EmployeeValidations();

    public List<EmployeePayRollRecord> searchByName(String name) {
        String filter = "%" + name + "%";
        return dslContext
                .selectFrom(EMPLOYEE_PAY_ROLL)
                .where(EMPLOYEE_PAY_ROLL.FIRST_NAME.likeIgnoreCase(filter)
                        .or(EMPLOYEE_PAY_ROLL.LAST_NAME.likeIgnoreCase(filter)))
                .fetchInto(EmployeePayRollRecord.class);
    }


    public List<Employee> get_all_employees() {

        List<Employee> all_employees = dslContext
                .select()
                .from(EMPLOYEE_PAY_ROLL)
                .fetch()
                .into(Employee.class);

        return all_employees;
    }

    public void add_employee(Employee employee) {

        //Validate new employee information
        employeeValidations.checkEmployeeInfo(this.get_all_employees(),employee);
        EmployeePayRollRecord new_employee = dslContext.newRecord(EMPLOYEE_PAY_ROLL,employee);
        new_employee.store();

    }

    public Employee findEmployeeById(Integer id) {

        //check if employee exists
        employeeValidations.checkEmployeeIdExist(this.get_all_employees(),id);

        Employee employee = new Employee();

        List<Employee> result = dslContext.select()
                .from(EMPLOYEE_PAY_ROLL)
                .where(EMPLOYEE_PAY_ROLL.ID.eq(id))
                .fetch()
                .into(Employee.class);


        return result.get(0);
    }

    public void updateEmployeeInfo(Employee employee){

        Boolean checkEmployeeSsn = false;

        EmployeePayRollRecord employeeToUpdate = dslContext
                .fetchOne(EMPLOYEE_PAY_ROLL,EMPLOYEE_PAY_ROLL.ID.eq(employee.getId()));

        if(employeeToUpdate.getSsn() != employee.getSsn()){
            checkEmployeeSsn = true;
        }

        employeeToUpdate.setFirstName(employee.getFirst_name());
        employeeToUpdate.setLastName(employee.getLast_name());
        employeeToUpdate.setDob(employee.getDob());
        employeeToUpdate.setSalary(employee.getSalary());
        employeeToUpdate.setSsn(employee.getSsn());
        employeeToUpdate.setStartDate(employee.getStart_date());

        Employee updatedEmployee = employeeToUpdate.into(Employee.class);

        if(checkEmployeeSsn)
            employeeValidations.checkEmployeeInfo(this.get_all_employees(),updatedEmployee);

        employeeToUpdate.store();
    }

    public void deleteEmployee(Integer id) {

        // Get a previously inserted employee
        EmployeePayRollRecord employeeToDelete = dslContext.fetchOne(EMPLOYEE_PAY_ROLL, EMPLOYEE_PAY_ROLL.ID.eq(id));

        // Delete the employee
        employeeToDelete.delete();
    }
}
