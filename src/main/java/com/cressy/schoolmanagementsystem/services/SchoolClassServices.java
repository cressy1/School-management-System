package com.cressy.schoolmanagementsystem.services;

import com.cressy.schoolmanagementsystem.dto.SchoolClassRequest;
import com.cressy.schoolmanagementsystem.dto.SchoolClassResponse;
import com.cressy.schoolmanagementsystem.entity.SchoolClasses;
import com.cressy.schoolmanagementsystem.enums.ClassCategory;
import com.cressy.schoolmanagementsystem.enums.Subjects;

import java.util.Set;

public interface SchoolClassServices {
    SchoolClassResponse createClass(SchoolClassRequest schoolClassRequest);
    void assignSubjectsToClass(SchoolClasses schoolClasses);
    Set<Subjects> getSeniorClassSubjects (ClassCategory category);
    //List<TaskDto> getTaskByClassId(Long id);

}
