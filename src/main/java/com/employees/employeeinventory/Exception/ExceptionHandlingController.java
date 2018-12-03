package com.employees.employeeinventory.Exception;

import com.employees.employeeinventory.Utils.Validations;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionHandlingController {

    private static Log logger = LogFactory.getLog(ExceptionHandlingController.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> resourceNotFound(ResourceNotFoundException ex){
        List<String> error = new ArrayList<>();
        ExceptionResponse response = new ExceptionResponse();
        logger.info("Sending 404 Not Found");
        response.setErrorCode("404");
        error.add(ex.getMessage());
        response.setErrors(error);

        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidEmployeeException.class)
    public ResponseEntity<ExceptionResponse> resourceNotValid(InvalidEmployeeException ex){
        List<String> error = new ArrayList<>();
        ExceptionResponse response = new ExceptionResponse();
        logger.info("Sending 409 Employee Conflict");
        response.setErrorCode("409");
        error.add(ex.getMessage());
        response.setErrors(error);

        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> invalidInput(MethodArgumentNotValidException ex){
        BindingResult result = ex.getBindingResult();
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorCode("400");
        response.setErrors(Validations.fromBindingErrors(result));
        return new ResponseEntity<ExceptionResponse>(response,HttpStatus.BAD_REQUEST);

    }


}
