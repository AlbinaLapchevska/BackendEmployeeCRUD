package com.example.project.exception;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(long id){
        super("Could not find employee with id "+id);

    }
}
