package com.cressy.schoolmanagementsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice

public class GlobalExceptionHandler {
    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(StudentNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SchoolClassNotFoundException.class)
    public ResponseEntity<String> handleClassNotFoundException(SchoolClassNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(StudentAlreadyExistsException.class)
    public ResponseEntity<String> handleStudentAlreadyExistsException(StudentAlreadyExistsException ex) {
        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.ALREADY_REPORTED);
    }

    @ExceptionHandler(SchoolClassAlreadyExistsException.class)
    public ResponseEntity<String> handleSchoolClassAlreadyExistsException(SchoolClassAlreadyExistsException ex) {
        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TeacherNotFoundException.class)
    public ResponseEntity<String> handleTeacherNotFoundException(TeacherNotFoundException ex) {
        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TeacherAlreadyExistsException.class)
    public ResponseEntity<String> handleTeacherAlreadyExistsException(TeacherAlreadyExistsException ex) {
        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.ALREADY_REPORTED);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<String> handleTaskNotFoundException(TaskNotFoundException ex) {
        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
