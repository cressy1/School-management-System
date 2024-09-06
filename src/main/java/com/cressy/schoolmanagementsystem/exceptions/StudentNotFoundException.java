package com.cressy.schoolmanagementsystem.exceptions;

public class StudentNotFoundException extends RuntimeException{
    public StudentNotFoundException(String message) {
        super(message);
    }
}
