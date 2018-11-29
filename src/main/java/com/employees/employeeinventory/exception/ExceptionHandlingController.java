package com.employees.employeeinventory.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.logging.Logger;

@ControllerAdvice
public class ExceptionHandlingController {

    private static Log logger = LogFactory.getLog(ExceptionHandlingController.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> resourceNotFound(ResourceNotFoundException ex){
        ExceptionResponse response = new ExceptionResponse();
        logger.info("Sending 404 Not Found");
        response.setErrorCode("404");
        response.setErrorMessage(ex.getMessage());

        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidEmployeeException.class)
    public ResponseEntity<ExceptionResponse> resourceNotValid(InvalidEmployeeException ex){
        ExceptionResponse response = new ExceptionResponse();
        logger.info("Sending 409 Employee Conflict");
        response.setErrorCode("409");
        response.setErrorMessage(ex.getMessage());

        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.CONFLICT);
    }



}
