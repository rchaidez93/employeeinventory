package com.employees.employeeinventory.Model;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

@Entity
@Data
@Table(name = "employee_pay_roll")
@EntityListeners(AuditingEntityListener.class)
public class Employee implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "First name may not be empty")
    @Column(name = "first_name")
    private String first_name;

    @NotBlank(message = "Last name may not be empty")
    @Column(name = "last_name")
    private String last_name;

    @Column(name = "dob")
    @NotBlank(message =  "Date of birth may not be empty")
    @Pattern(regexp = "^\\d{1,2}\\/\\d{1,2}\\/\\d{4}$",message = "Date of birth must be in dd/mm/yyyy format")
    private String dob;

    @Column(name = "ssn",nullable = false)
    private int ssn;

    @Column(name = "start_date")
    @NotBlank(message = "Start date may not be null")
    @Pattern(regexp = "^\\d{1,2}\\/\\d{1,2}\\/\\d{4}$",message = "Start date must be in dd/mm/yyyy format")
    private String start_date;

    @Column(name = "salary")
    @Min(value = 0,message = "Salary cannot be a negative number")
    private int salary;

    @Min(value = 0, message = "Supervisor can only be a 0 for no and 1 for yes")
    @Max(value = 1, message = "Supervisor can only be a 0 for no and 1 for yes")
    private int supervisor;

    private int supervisor_id;

    @Transient
    private List<Supervising> supervising;

    @Transient
    private List<Supervisor> employee_supervisor;

    @Override
    public String toString() {
        String full_employee_list = "Employee{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", dob='" + dob + '\'' +
                ", ssn=" + ssn +
                ", start_date='" + start_date + '\'' +
                ", salary=" + salary +
                ", supervisor=" + supervisor +
                ", supervisor_id=" + supervisor_id;
//                '}';
        try{
            full_employee_list += (supervising.size() > 0) ? ",Supervising[" : "";
            for(Supervising supervising : supervising){
                full_employee_list += "{" + supervising.toString() + "}";
            }
            full_employee_list += "]}";
        }catch (NullPointerException np){
            System.out.println(np);
        }

        try{
            full_employee_list += (employee_supervisor.size() > 0) ? ",Supervisor[" : "";
            for(Supervisor supervisor : employee_supervisor){
                full_employee_list += "{" + supervisor.toString() + "}";
            }
            full_employee_list += "]}";
        }catch(NullPointerException np){
            System.out.println(np);
        }


        return full_employee_list;
    }
}
