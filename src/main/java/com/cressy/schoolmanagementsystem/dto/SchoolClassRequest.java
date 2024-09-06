package com.cressy.schoolmanagementsystem.dto;

import com.cressy.schoolmanagementsystem.enums.ClassCategory;
import com.cressy.schoolmanagementsystem.enums.ClassType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolClassRequest {
    private String className;
    private ClassType classType;
    private ClassCategory classCategory;
}

