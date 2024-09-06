package com.cressy.schoolmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String temporaryPassword;
    private int age;
    private String phoneNumber;
    private String qualification;
    private String teacherType;
    private String roles;
    private String className;
    private String subjects;
    private String classCategory;
}
