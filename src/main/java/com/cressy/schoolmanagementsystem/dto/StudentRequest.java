package com.cressy.schoolmanagementsystem.dto;

import com.cressy.schoolmanagementsystem.enums.Roles;
import com.cressy.schoolmanagementsystem.enums.StudentType;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequest {
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String temporaryPassword;
    private int age;
    private String phoneNumber;
    private StudentType studentType;
    private Roles roles;

}
