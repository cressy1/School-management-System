package com.cressy.schoolmanagementsystem.services.ServiceImplementations;

import com.cressy.schoolmanagementsystem.controller.studentsController.Student;
import com.cressy.schoolmanagementsystem.dto.StudentRequest;
import com.cressy.schoolmanagementsystem.dto.StudentResponse;
import com.cressy.schoolmanagementsystem.dto.TaskDto;
import com.cressy.schoolmanagementsystem.entity.SchoolClasses;
import com.cressy.schoolmanagementsystem.entity.Students;
import com.cressy.schoolmanagementsystem.entity.Task;
import com.cressy.schoolmanagementsystem.enums.TaskStatus;
import com.cressy.schoolmanagementsystem.exceptions.SchoolClassNotFoundException;
import com.cressy.schoolmanagementsystem.exceptions.StudentAlreadyExistsException;
import com.cressy.schoolmanagementsystem.exceptions.StudentNotFoundException;
import com.cressy.schoolmanagementsystem.repository.SchoolClassesRepository;
import com.cressy.schoolmanagementsystem.repository.StudentRepository;
import com.cressy.schoolmanagementsystem.repository.TaskRepository;
import com.cressy.schoolmanagementsystem.services.StudentServices;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentServices {
    private final StudentRepository studentRepository;
    private final SchoolClassesRepository schoolClassesRepository;
    private final TaskRepository taskRepository;


    @Override
    public StudentResponse registerStudent(StudentRequest studentRequest) {
        Optional<Students> existingStudent = studentRepository.findByEmail(studentRequest.getEmail());
        if (existingStudent.isPresent()) {
            throw new StudentAlreadyExistsException("Student with " + studentRequest.getEmail() + " already exists.");
        }

        Students newStudents = Students.builder()
                .firstName(studentRequest.getFirstName())
                .middleName(studentRequest.getMiddleName())
                .lastName(studentRequest.getLastName())
                .email(studentRequest.getEmail())
                .temporaryPassword(studentRequest.getTemporaryPassword())
                .age(studentRequest.getAge())
                .phoneNumber(studentRequest.getPhoneNumber())
                .studentType(studentRequest.getStudentType())
                .studentNumber(generateStudentNumber())
                .roles(studentRequest.getRoles())
                .build();

        Students newStudent = studentRepository.save(newStudents);

        return StudentResponse.builder()
                .firstName(newStudents.getFirstName())
                .middleName(newStudents.getMiddleName())
                .lastName(newStudents.getLastName())
                .email(newStudents.getEmail())
                .age(newStudents.getAge())
                .phoneNumber(newStudents.getPhoneNumber())
                .StudentNumber(newStudent.getStudentNumber())
                .studentRole(newStudent.getRoles().toString())
                .build();
    }

    @Override
    public StudentResponse assignStudentToClass(String studentNumber, Long classId) {
        Students students = studentRepository.findByStudentNumber(studentNumber)
                .orElseThrow(() -> new StudentNotFoundException("Student Not Found"));
        SchoolClasses schoolClasses = schoolClassesRepository.findById(classId)
                .orElseThrow(() -> new SchoolClassNotFoundException("Class Not Found"));

        if (students.getSchoolClasses() != null
                && students.getSchoolClasses().getId().equals(classId)){
            throw new StudentAlreadyExistsException("This student is already assigned to this class");
        }

        if (students.getSchoolClasses() != null
                && !students.getSchoolClasses().getId().equals(classId)) {
            throw new StudentAlreadyExistsException("This student is already assigned to " + schoolClasses.getClassName()
                    + " .you must remove student from that class before reassigning to another class.");
        }

        students.setSchoolClasses(schoolClasses);
        students.setClassName(schoolClasses.getClassName());
        students.setClassType(String.valueOf(schoolClasses.getClassType()));
        students.setCategory(String.valueOf(schoolClasses.getClassCategory()));

        schoolClasses.setStudentCount(schoolClasses.getStudentCount() + 1);

        Students updatedStudent = studentRepository.save(students);
        SchoolClasses updatedSchoolClass = schoolClassesRepository.save(schoolClasses);

        return StudentResponse.builder()
                .firstName(updatedStudent.getFirstName())
                .middleName(updatedStudent.getMiddleName())
                .lastName(updatedStudent.getLastName())
                .age(updatedStudent.getAge())
                .StudentNumber(updatedStudent.getStudentNumber())
                .className(updatedStudent.getClassName())
                .classType(updatedStudent.getClassType())
                .category(updatedStudent.getCategory())
                .studentRole(updatedStudent.getRoles().toString())
                .numberOfStudentsInClass(updatedSchoolClass.getStudentCount())
                .build();
    }

    @Override
    public void removeStudentFromClass(String studentNumber, Long classId) {
        Students students = studentRepository.findByStudentNumber(studentNumber).orElseThrow(
                ()-> new StudentNotFoundException("Student not found")
        );
        SchoolClasses schoolClasses = schoolClassesRepository.findById(classId).orElseThrow(
                ()-> new SchoolClassNotFoundException("This class does not exists")
        );

        if (students.getSchoolClasses() == null || !students.getSchoolClasses().getId().equals(classId)){
            throw new IllegalStateException("Student already removed from this class");
        }

        students.setSchoolClasses(null);
        schoolClasses.setStudentCount(schoolClasses.getStudentCount() - 1);

        studentRepository.save(students);
        schoolClassesRepository.save(schoolClasses);
    }

    @Override
    public String generateStudentNumber() {
        int currentYear = Year.now().getValue();
        String baseNumber = "111101992";

        // Fetch the last registered student's number
        Optional<Students> lastStudent = studentRepository.findTopByOrderByStudentNumberDesc();

        String newStudentNumber;
        if (lastStudent.isPresent()) {
            String lastNumber = lastStudent.get().getStudentNumber();
            long incrementedNumber = Long.parseLong(lastNumber.substring(4)) + 1;
            newStudentNumber = currentYear + String.valueOf(incrementedNumber);
        } else {
            newStudentNumber = currentYear + baseNumber;
        }
        return newStudentNumber;
    }

//    @Override
//    public List<StudentResponse> getAllStudentsByClass(Long classId) {
//        List<Students> students = studentRepository.getAllStudentsByClassId(classId);
//        return students.stream().map(this::mapToStudentResponse).toList();
//    }

    @Override
    public List<StudentResponse> getAllStudents() {
        List<Students> students = studentRepository.findAll();
        return students.stream()
                .map(this::mapToStudentResponse)
                .toList();
    }

    @Override
    public Optional<StudentResponse> findByStudentNumber(String studentNumber) {
        Students students = studentRepository.findByStudentNumber(studentNumber).orElseThrow(()->
                new StudentNotFoundException("Student with student number " + studentNumber + " not found."));
        return Optional.ofNullable(mapToStudentResponse(students));

    }

    @Override
    public List<TaskDto> getTaskByTaskClassId(Long classId) {
        List<Task> tasks = taskRepository.findBySchoolClassesId(classId);
        return tasks.stream()
                .map(Task::getTaskResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDto updateTaskStatus(Long id, String status) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()){
            Task existingTask = task.get();
            existingTask.setTaskStatus(mapStringToTaskStatus(status));
            return taskRepository.save(existingTask).getTaskResponse();

        }
        throw new EntityNotFoundException("Task not found");
    }

    @Override
    @Transactional
    public String  deleteStudent(String studentNumber) {
        Students student = studentRepository.findByStudentNumber(studentNumber)
                .orElseThrow(() -> new StudentNotFoundException("Student with student number " + studentNumber + " not found"));

        // Fetch the school class associated with the student
        SchoolClasses schoolClasses = student.getSchoolClasses();

        // Check if the student is assigned to a class
        if (schoolClasses != null) {
            // Remove the student from the class
            schoolClasses.getStudents().remove(student);

            // Decrement the student count in the class
            if (schoolClasses.getStudentCount() > 0) {
                schoolClasses.setStudentCount(schoolClasses.getStudentCount() - 1);
            }

            // Save the updated school class
            schoolClassesRepository.save(schoolClasses);
        }

        // Delete the student from the repository
        studentRepository.delete(student);

        return student.getFirstName() + " " + student.getLastName() + " deleted successfully";
    }

    @Override
    public StudentResponse updateStudent(String studentNumber, StudentRequest studentRequest) {
        Optional<Students> updateStudent = studentRepository.findByStudentNumber(studentNumber);
        if (updateStudent.isPresent()){
            Students existingStudent = updateStudent.get();
            existingStudent.setFirstName(studentRequest.getFirstName());
            existingStudent.setMiddleName(studentRequest.getMiddleName());
            existingStudent.setLastName(studentRequest.getLastName());
            existingStudent.setEmail(studentRequest.getEmail());
            existingStudent.setAge(studentRequest.getAge());
            existingStudent.setPhoneNumber(studentRequest.getPhoneNumber());
            existingStudent.setStudentType(studentRequest.getStudentType());
            existingStudent.setRoles(studentRequest.getRoles());

            Students updatedStudent = studentRepository.save(existingStudent);

            return mapToStudentResponse(updatedStudent);
        }
        throw new StudentNotFoundException("Student with student number " + studentNumber + " not found.");
    }

    private StudentResponse mapToStudentResponse(Students student) {
        return StudentResponse.builder()
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .age(student.getAge())
                .StudentNumber(student.getStudentNumber())
                .email(student.getEmail())
                .studentRole(student.getRoles().toString())
                .className(student.getClassName())
                .classType(student.getClassType())
                .category(student.getCategory())
                .build();
    }

    private TaskStatus mapStringToTaskStatus(String status) {
        return switch (status) {
            case "PENDING" -> TaskStatus.PENDING;
            case "IN_PROGRESS" -> TaskStatus.IN_PROGRESS;
            case "COMPLETED" -> TaskStatus.COMPLETED;
            case "DEFERRED" -> TaskStatus.DEFERRED;
            default -> TaskStatus.CANCELLED;
        };
    }
}



//Create a method for a logged in student to get task by class id of his particular class, ie if the student is not in that class he should be unauthorized
