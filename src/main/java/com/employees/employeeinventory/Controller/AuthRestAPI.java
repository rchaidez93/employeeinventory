package com.employees.employeeinventory.Controller;


import com.employees.employeeinventory.JwtMessage.Request.LoginForm;
import com.employees.employeeinventory.JwtMessage.Request.SignUpForm;
import com.employees.employeeinventory.JwtMessage.Response.JwtResponse;
import com.employees.employeeinventory.Model.Role;
import com.employees.employeeinventory.Model.RoleName;
import com.employees.employeeinventory.Model.User;
import com.employees.employeeinventory.Security.Jwt.JwtProvider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthRestAPI {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    private UserServices userServices;

    @Autowired
    private RoleServices roleServices;

    private static Log logger = LogFactory.getLog(AuthRestAPI.class);


    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {

        logger.info(signUpRequest.toString());
        logger.error("Am i reachable??");

        if(userServices.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<String>("Fail -> Username is already taken!",
                    HttpStatus.BAD_REQUEST);
        }

        logger.info("reached here");

        // Creating user's account
        User user = new User(signUpRequest.getUsername(),encoder.encode(signUpRequest.getPassword()));
        System.out.println(user.toString());

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();



        strRoles.forEach(role -> {
            logger.info("in foreach");
            System.out.println(role);
            switch(role) {
                case "admin":
                    Role adminRole = roleServices.findByName(RoleName.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(adminRole);

                    break;
                case "pm":
                    Role pmRole = roleServices.findByName(RoleName.ROLE_PM)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(pmRole);

                    break;
                default:
                    Role userRole = roleServices.findByName(RoleName.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(userRole);
            }
        });

        user.setRoles(roles);
        userServices.saveUser(user);

        return ResponseEntity.ok().body("User registered successfully!");
    }


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

        logger.info(loginRequest.getPassword());
        logger.info(loginRequest.getUsername());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        logger.info("Here");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }


}
