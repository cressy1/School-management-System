package com.cressy.schoolmanagementsystem.dto;

import com.cressy.schoolmanagementsystem.enums.Subjects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.security.auth.Subject;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SchoolClassResponse {
    private Long id;
    private String className;
    private String classType;
    private String classCategory;
    private Set<Subjects> subjects;

}
