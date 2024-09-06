package com.cressy.schoolmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentResponse {
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private int age;
    private String phoneNumber;
    private String StudentNumber;
    private String studentRole;
    private String className;
    private String classType;
    private String category;

}
