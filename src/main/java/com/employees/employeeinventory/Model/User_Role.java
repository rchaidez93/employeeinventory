package com.employees.employeeinventory.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;


@Data
@AllArgsConstructor
public class User_Role {

    private Long user_Id;

    private Long role_id;
}
