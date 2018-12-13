package com.employees.employeeinventory.Repository;

import com.employees.employeeinventory.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query(value = "select * from employee_pay_roll e where e.supervisor_id = ?1",nativeQuery = true)
    List<Employee> findSupervisorById(Integer supervisor_id);
}
