package com.employees.employeeinventory.Controller;

import com.employees.employeeinventory.Model.Role;
import com.employees.employeeinventory.Model.RoleName;
import com.employees.employeeinventory.Model.User;
import com.employees.employeeinventory.Model.User_Role;
import com.employees.tables.records.RoleRecord;
import com.employees.tables.records.UserRecord;
import com.employees.tables.records.UserRolesRecord;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collector;

import static com.employees.Tables.USER;
import static com.employees.Tables.USER_ROLES;

@Component
public class UserServices {

    @Autowired
    private DSLContext dslContext;
    private static Log logger = LogFactory.getLog(UserServices.class);

    @Autowired
    private RoleServices roleServices;

    public User findByUsername(String username){

        User user = new User();
        logger.info("username: " + username);


        //need to get username and password and user roles
        //this query just gets the username and password
        UserRecord getUser = dslContext
                .select()
                .from(USER)
                .where(USER.USERNAME.eq(username))
                .fetchOne()
                .into(UserRecord.class);

        user.setId(getUser.getId());
        user.setUsername(getUser.getUsername());
        user.setPassword(getUser.getPassword());

        Set<Role> roles = new HashSet<>();

        Result<Record1<Long>> roles_result = dslContext
                .select(USER_ROLES.ROLE_ID)
                .from(USER_ROLES)
                .where(USER_ROLES.USER_ID.eq(getUser.getId()))
                .fetch();

       for(Record1 record1: roles_result){

           if(record1.value1().equals(3L)){
               roles.add(new Role(RoleName.ROLE_ADMIN));
           }else if(record1.value1().equals(2L)){
               roles.add(new Role(RoleName.ROLE_PM));
           }
           else{
               roles.add(new Role(RoleName.ROLE_USER));
           }
           user.setRoles(roles);
       }



        return user;
    }

    public boolean existsByUsername(String username) {
        List<User> result = dslContext
                .select()
                .from(USER)
                .where(USER.USERNAME.eq(username))
                .fetch()
                .into(User.class);

        if(result.isEmpty())
            return false;
        else
            return true;

    }

    public void saveUser(User user) {


        logger.info("in saveUser");
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        System.out.println(user.getId());

        List<UserRecord> allRecords = dslContext
                .select()
                .from(USER)
                .fetchInto(UserRecord.class);
        System.out.println(allRecords);

        Long user_record_size = Long.valueOf(allRecords.size());
        System.out.println(user_record_size);

        //insert row to users table
        UserRecord userRecord = dslContext.newRecord(USER);
        if(user_record_size < 1){
            userRecord.setId(1L);
        }
        else{
            Long last_insert_id = allRecords.get(Math.toIntExact(user_record_size) - 1).getId();
            userRecord.setId(last_insert_id+1);
        }
        userRecord.setUsername(user.getUsername());
        userRecord.setPassword(user.getPassword());
        userRecord.store();

//        TODO : add entry to user role table. Issue : Constaint "constraint" foreign key (user_id) references `user`(id)


    }
}
