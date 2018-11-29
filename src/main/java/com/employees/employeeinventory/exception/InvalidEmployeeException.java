package com.employees.employeeinventory.exception;

public class InvalidEmployeeException extends RuntimeException {

    private Integer resourceId;

    public InvalidEmployeeException(Integer resourceId, String message) {
        super(message);
        this.resourceId = resourceId;
    }
}
