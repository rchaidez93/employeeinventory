package com.employees.employeeinventory.Controller;

import com.employees.employeeinventory.Model.Role;
import com.employees.employeeinventory.Model.RoleName;
import com.employees.employeeinventory.Model.User;
import com.employees.tables.records.RoleRecord;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.employees.Tables.ROLE;
import static com.employees.Tables.USER;
import static com.employees.Tables.USER_ROLES;


@Component
public class RoleServices {

    @Autowired
    private DSLContext dslContext;
    private static Log logger = LogFactory.getLog(RoleServices.class);


    Optional<Role> findByName(RoleName roleName){

        List<Role> result = dslContext
                .select(ROLE.ID)
                .from(ROLE)
                .where(ROLE.NAME.eq(roleName.toString()))
                .fetch()
                .into(Role.class);

        return Optional.ofNullable(result.get(0));
    }



}
