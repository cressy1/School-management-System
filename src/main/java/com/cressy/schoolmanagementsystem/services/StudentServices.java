package com.cressy.schoolmanagementsystem.services;

import com.cressy.schoolmanagementsystem.dto.*;

import java.util.List;
import java.util.Optional;

public interface StudentServices {
    StudentResponse registerStudent(StudentRequest studentRequest);

    StudentResponse assignStudentToClass(String studentNumber, Long classId);
    String generateStudentNumber();
    List<StudentResponse> getAllStudents();
    Optional<StudentResponse> findByStudentNumber(String studentNumber);
    List<TaskDto> getTaskByTaskClassId(Long classId);
    TaskDto updateTaskStatus(Long id, String status);
    void deleteStudent(String studentNumber);
    StudentResponse updateStudent(String studentNumber, StudentRequest studentRequest);

}
