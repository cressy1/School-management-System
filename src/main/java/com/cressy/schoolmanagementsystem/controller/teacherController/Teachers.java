package com.cressy.schoolmanagementsystem.controller.teacherController;

import com.cressy.schoolmanagementsystem.dto.StudentResponse;
import com.cressy.schoolmanagementsystem.dto.TaskDto;
import com.cressy.schoolmanagementsystem.dto.TeacherRequest;
import com.cressy.schoolmanagementsystem.dto.TeacherResponse;
import com.cressy.schoolmanagementsystem.enums.Subjects;
import com.cressy.schoolmanagementsystem.services.TeacherServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/teachers")
public class Teachers {
    private final TeacherServices teacherServices;

    @PostMapping("/register")
    public ResponseEntity<TeacherResponse> registerTeacher(@RequestBody TeacherRequest teacherRequest) {
        TeacherResponse teacherResponse = teacherServices.registerTeacher(teacherRequest);
        return new ResponseEntity<>(teacherResponse, HttpStatus.CREATED);
    }

    @PostMapping("/assignTeacherToClass/{teacherId}")
    public ResponseEntity<TeacherResponse> assignTeacherToClass(@PathVariable Long teacherId,
                                                                @RequestBody List<Long> classId){
        TeacherResponse teacherResponse = teacherServices.assignTeacherToClasses(teacherId, classId);
        return new ResponseEntity<>(teacherResponse, HttpStatus.OK);
    }

    @PostMapping("/assignTeacherToSubject")
    public ResponseEntity<TeacherResponse> assignTeacherToSubjects(@RequestParam Long teacherId,
                                                                   @RequestBody List<Subjects> subjectsList) {
        TeacherResponse teacherResponse = teacherServices.assignTeacherToSubjects(teacherId, subjectsList);
        return new ResponseEntity<>(teacherResponse, HttpStatus.OK);
    }

    @PostMapping("/assignTaskToClass")
    public ResponseEntity<TaskDto> assignTaskToClass(@RequestBody TaskDto taskDto) {
        TaskDto createdTask = teacherServices.createTask(taskDto);
        if (taskDto == null)
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @GetMapping("/getAllTasks")
    public ResponseEntity<?> getAllTasks(){
        return ResponseEntity.ok(teacherServices.getAllTasks());
    }

    @DeleteMapping("/deleteTask/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        teacherServices.deleteTask(id);
        return ResponseEntity.ok(null);
    }
    @GetMapping("/getTask/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id){
        return ResponseEntity.ok(teacherServices.getTaskById(id));
    }

    @PutMapping("/updateTask/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id,
                                        @RequestBody TaskDto taskDto) {
        TaskDto updateTask = teacherServices.updateTask(id, taskDto);
        if (updateTask == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updateTask);

    }
    @GetMapping("/searchTasks/{title}")
    public ResponseEntity<List<TaskDto>> searchTask(@PathVariable String title) {
        return ResponseEntity.ok(teacherServices.searchTaskByTitle(title));
    }

    @GetMapping("/tasks/byClass/{classId}")
    public ResponseEntity<List<TaskDto>> getTasksByClassId(@PathVariable("classId") Long id) {
        List<TaskDto> tasks = teacherServices.getTaskByTaskClassId(id);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @DeleteMapping("/deleteTeacher/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        teacherServices.deleteTeacher(id);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/getAllTeachers")
    public ResponseEntity<?> getAllTeachers(){
        return ResponseEntity.ok(teacherServices.getAllTeachers());
    }

    @GetMapping("/getTeacher/{id}")
    public ResponseEntity<Optional<TeacherResponse>> getTeacherById(@PathVariable Long id){
        return ResponseEntity.ok(teacherServices.getTeacherById(id));
    }

    @PutMapping("/updateTeacher/{id}")
    public ResponseEntity<TeacherResponse> updateTeacher(@PathVariable Long id,
                                        @RequestBody TeacherRequest teacherRequest) {
        TeacherResponse updateTeacher = teacherServices.updateTeacher(id, teacherRequest);
        if (updateTeacher == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updateTeacher);

    }
    @GetMapping("/allStudentsInAClass")
    public ResponseEntity<List<StudentResponse>> getAllStudentsByClassName(@RequestParam String className) {
       List<StudentResponse> allStudentsInAClass =teacherServices.getAllStudentsByClassName(className);
       return ResponseEntity.ok(allStudentsInAClass);
    }
}
