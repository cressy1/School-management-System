package com.cressy.schoolmanagementsystem.services;

import com.cressy.schoolmanagementsystem.dto.StudentResponse;
import com.cressy.schoolmanagementsystem.dto.TaskDto;
import com.cressy.schoolmanagementsystem.dto.TeacherRequest;
import com.cressy.schoolmanagementsystem.dto.TeacherResponse;
import com.cressy.schoolmanagementsystem.enums.Subjects;

import java.util.List;
import java.util.Optional;

public interface TeacherServices {
    TeacherResponse assignTeacherToClasses(Long teacherId, List<Long> classIds);
    void removeTeacherFromClass(Long teacherId, List<Long> classIds);
    TeacherResponse assignTeacherToSubjects(Long teacherId, List<Subjects> subjects);
    TeacherResponse registerTeacher(TeacherRequest teacherRequest);
    Optional<TeacherResponse> getTeacherById(Long id);
    List<TeacherResponse> getAllTeachers();
    TaskDto createTask(TaskDto taskDto);
    List<TaskDto> getAllTasks();
    void deleteTask(Long id);
    TaskDto getTaskById(Long id);
    TaskDto updateTask(Long id, TaskDto taskDto);
    List<TaskDto> searchTaskByTitle(String title);
    List<TaskDto> getTaskByTaskClassId(Long classId);
    String deleteTeacher(Long id);
    TeacherResponse updateTeacher(Long id, TeacherRequest teacherRequest);
    List<StudentResponse> getAllStudentsByClassName(String className);



    //List<TaskDto> getTaskByClassId(Long id);


}
