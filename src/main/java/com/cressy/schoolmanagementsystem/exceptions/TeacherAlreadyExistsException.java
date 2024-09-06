package com.cressy.schoolmanagementsystem.exceptions;

public class TeacherAlreadyExistsException extends RuntimeException{
    public TeacherAlreadyExistsException(String meessage) {
        super(meessage);
    }
}
