package com.employees.employeeinventory.Utils;

import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

public class Validations {

    public static List<String> fromBindingErrors(Errors errors){
        List<String> validErrors = new ArrayList<>();
        for(ObjectError objectError : errors.getAllErrors()){
            validErrors.add(objectError.getDefaultMessage());
        }
        return validErrors;
    }
}
