package com.cressy.schoolmanagementsystem.controller.studentsController;

import com.cressy.schoolmanagementsystem.dto.*;
import com.cressy.schoolmanagementsystem.exceptions.SchoolClassNotFoundException;
import com.cressy.schoolmanagementsystem.exceptions.StudentNotFoundException;
import com.cressy.schoolmanagementsystem.services.SchoolClassServices;
import com.cressy.schoolmanagementsystem.services.StudentServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/students")
public class Student {
    private final StudentServices studentServices;
    private final SchoolClassServices schoolClassServices;

    @PostMapping("/register")
    public ResponseEntity<StudentResponse> registerNewStudent(@RequestBody StudentRequest studentRequest) {
        StudentResponse studentResponse = studentServices.registerStudent(studentRequest);

        return new ResponseEntity<>(studentResponse, HttpStatus.CREATED);
    }

    @PostMapping("/createClass")
    public ResponseEntity<SchoolClassResponse> createClass(@RequestBody SchoolClassRequest schoolClassRequest) {
        SchoolClassResponse schoolClassResponse = schoolClassServices.createClass(schoolClassRequest);

        return new ResponseEntity<>(schoolClassResponse, HttpStatus.CREATED);
    }
    @PostMapping("/assignStudentsToClass/{studentNumber}/{classId}")
    public ResponseEntity<StudentResponse> assignStudentToClass(@PathVariable("studentNumber") String studentNumber,
                                                                @PathVariable("classId") Long classId) {
        StudentResponse studentResponse = studentServices.assignStudentToClass(studentNumber, classId);
        return new ResponseEntity<>(studentResponse, HttpStatus.OK);
    }

    @DeleteMapping("/remove-student/{studentNumber}/{classId}")
    public ResponseEntity<String> removeStudentFromClas(@PathVariable String studentNumber,
                                                        @PathVariable Long classId) {
        try {
            studentServices.removeStudentFromClass(studentNumber, classId);
            return ResponseEntity.ok("Student removed from class successfully");
        }catch (IllegalStateException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }catch (StudentNotFoundException | SchoolClassNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while removing the student from the class");
        }
    }

    @GetMapping("/all")
    ResponseEntity<List<StudentResponse>> getAllStudents(){
            List<StudentResponse> allStudents = studentServices.getAllStudents();
            return new ResponseEntity<>(allStudents, HttpStatus.OK);
    }

    @GetMapping("/{studentNumber}")
    public ResponseEntity<Optional<StudentResponse>> getStudentByStudentNumber(@PathVariable String studentNumber) {
            Optional<StudentResponse> getStudent = studentServices.findByStudentNumber(studentNumber);
            return new ResponseEntity<>(getStudent, HttpStatus.OK);
    }

    @GetMapping("/tasks/byClass/{classId}")
    public ResponseEntity<List<TaskDto>> getTasksByClassId(@PathVariable("classId") Long id) {
        List<TaskDto> tasks = studentServices.getTaskByTaskClassId(id);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }
    @GetMapping("/tasks/{id}/{status}")
    public ResponseEntity<TaskDto> updateTaskStatus(@PathVariable Long id, @PathVariable String status){
        TaskDto updateTaskStatus = studentServices.updateTaskStatus(id, status);
        if (updateTaskStatus == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.ok(updateTaskStatus);
        }

//    public ResponseEntity<List<TaskDto>> getTaskByClassId(@PathVariable Long Id) {
//        return ResponseEntity.ok(schoolClassServices.getTaskByClassId())
//    }

    @DeleteMapping("/deleteStudent/{studentNumber}")
    public ResponseEntity<String> deleteStudent(@PathVariable String studentNumber) {
        String deleteStudent = studentServices.deleteStudent(studentNumber);
        return new ResponseEntity<>(deleteStudent, HttpStatus.OK);
    }
    @PutMapping("/updateStudent/{studentNumber}")
    public ResponseEntity<StudentResponse> updateStudent(@PathVariable String studentNumber, @RequestBody StudentRequest studentRequest) {
        StudentResponse updateStudent = studentServices.updateStudent(studentNumber, studentRequest);
        if (updateStudent== null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updateStudent);
    }
    }


