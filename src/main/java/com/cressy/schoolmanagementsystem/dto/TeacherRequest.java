package com.cressy.schoolmanagementsystem.dto;

import com.cressy.schoolmanagementsystem.entity.SchoolClasses;
import com.cressy.schoolmanagementsystem.enums.ClassCategory;
import com.cressy.schoolmanagementsystem.enums.Roles;
import com.cressy.schoolmanagementsystem.enums.TeacherType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String temporaryPassword;
    private int age;
    private String phoneNumber;
    private String qualification;
    private TeacherType teacherType;
    private Roles roles;
    private SchoolClasses schoolClasses;
    private ClassCategory classCategory;
}
