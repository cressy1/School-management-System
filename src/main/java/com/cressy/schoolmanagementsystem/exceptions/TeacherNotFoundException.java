package com.cressy.schoolmanagementsystem.exceptions;

public class TeacherNotFoundException extends RuntimeException{
    public TeacherNotFoundException(String message){
        super(message);
    }
}
