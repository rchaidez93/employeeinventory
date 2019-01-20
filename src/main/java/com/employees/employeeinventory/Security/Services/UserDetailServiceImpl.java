package com.employees.employeeinventory.Security.Services;

import com.employees.employeeinventory.Controller.UserServices;
import com.employees.employeeinventory.Model.User;
import com.employees.employeeinventory.Security.WebSecurityConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private static Log logger = LogFactory.getLog(UserDetailServiceImpl.class);

    @Autowired
    private UserServices userServices;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.info("in loadUserbyusername");
        //find user by username
        User user = userServices.findByUsername(username);


        return UserPrinciple.build(user);
    }
}
