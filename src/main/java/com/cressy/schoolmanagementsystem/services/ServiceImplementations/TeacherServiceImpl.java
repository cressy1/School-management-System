package com.cressy.schoolmanagementsystem.services.ServiceImplementations;

import com.cressy.schoolmanagementsystem.dto.StudentResponse;
import com.cressy.schoolmanagementsystem.dto.TaskDto;
import com.cressy.schoolmanagementsystem.dto.TeacherRequest;
import com.cressy.schoolmanagementsystem.dto.TeacherResponse;
import com.cressy.schoolmanagementsystem.entity.SchoolClasses;
import com.cressy.schoolmanagementsystem.entity.Students;
import com.cressy.schoolmanagementsystem.entity.Task;
import com.cressy.schoolmanagementsystem.entity.Teachers;
import com.cressy.schoolmanagementsystem.enums.Subjects;
import com.cressy.schoolmanagementsystem.enums.TaskStatus;
import com.cressy.schoolmanagementsystem.exceptions.SchoolClassNotFoundException;
import com.cressy.schoolmanagementsystem.exceptions.TeacherAlreadyExistsException;
import com.cressy.schoolmanagementsystem.exceptions.TeacherNotFoundException;
import com.cressy.schoolmanagementsystem.repository.SchoolClassesRepository;
import com.cressy.schoolmanagementsystem.repository.StudentRepository;
import com.cressy.schoolmanagementsystem.repository.TaskRepository;
import com.cressy.schoolmanagementsystem.repository.TeacherRepository;
import com.cressy.schoolmanagementsystem.services.TeacherServices;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherServices {
    private final TeacherRepository teacherRepository;
    private final SchoolClassesRepository schoolClassesRepository;
    private final StudentRepository studentRepository;
    private final TaskRepository taskRepository;

    @Override
    public TeacherResponse registerTeacher(TeacherRequest teacherRequest) {
        Optional<Teachers> existingTeachers = teacherRepository.findByEmail(teacherRequest.getEmail());
        if (existingTeachers.isPresent()) {
            throw new TeacherAlreadyExistsException("Teacher with the email: " + teacherRequest.getEmail() + " already exists.");
        }

        Teachers teachers = Teachers.builder()
                .firstName(teacherRequest.getFirstName())
                .lastName(teacherRequest.getLastName())
                .email(teacherRequest.getEmail())
                .temporaryPassword(teacherRequest.getTemporaryPassword())
                .age(teacherRequest.getAge())
                .phoneNumber(teacherRequest.getPhoneNumber())
                .qualification(teacherRequest.getQualification())
                .type(teacherRequest.getTeacherType())
                .roles(teacherRequest.getRoles())
                .classCategory(teacherRequest.getClassCategory())

                .build();

        Teachers newTeacher = teacherRepository.save(teachers);

        return TeacherResponse.builder()
                .id(newTeacher.getId())
                .firstName(newTeacher.getFirstName())
                .lastName(newTeacher.getLastName())
                .email(newTeacher.getEmail())
                .temporaryPassword(newTeacher.getTemporaryPassword())
                .age(newTeacher.getAge())
                .phoneNumber(newTeacher.getPhoneNumber())
                .qualification(newTeacher.getQualification())
                .teacherType(newTeacher.getType().toString())
                .roles(newTeacher.getRoles().toString())
                .classCategory(newTeacher.getClassCategory().toString())
                .build();

    }


    @Override
    public TeacherResponse assignTeacherToClasses(Long teacherId, List<Long> classIds) {
        Teachers teachers = teacherRepository.findById(teacherId).orElseThrow(()->
                new TeacherNotFoundException("Teacher with " + teacherId + " not found"));

        Set<SchoolClasses> existingClasses = teachers.getSchoolClasses();
        Set<SchoolClasses> classes = new HashSet<>();
        for (Long classId : classIds) {
            SchoolClasses schoolClasses = schoolClassesRepository.findById(classId).orElseThrow(()->
                    new SchoolClassNotFoundException("Class with classId: " + classId + " not found"));

            // Check if the teacher is already assigned to this class
            if (existingClasses.contains(schoolClasses)) {
                throw new IllegalStateException("This teacher is already assigned to " + schoolClasses.getClassName());
            }
            classes.add(schoolClasses);
            schoolClasses.getTeachers().add(teachers);
            schoolClasses.setTeacherCount(schoolClasses.getTeacherCount() + 1);
        }

        existingClasses.addAll(classes);
        teachers.setSchoolClasses(existingClasses);
        Teachers updatedTeacher = teacherRepository.save(teachers);

        return TeacherResponse.builder()
                .id(updatedTeacher.getId())
                .firstName(updatedTeacher.getFirstName())
                .lastName(updatedTeacher.getLastName())
                .age(updatedTeacher.getAge())
                .email(updatedTeacher.getEmail())
                .temporaryPassword(updatedTeacher.getTemporaryPassword())
                .phoneNumber(updatedTeacher.getPhoneNumber())
                .qualification(updatedTeacher.getQualification())
                .teacherType(updatedTeacher.getType().toString())
                .roles(updatedTeacher.getRoles().toString())
                .className(updatedTeacher.getSchoolClasses().stream().map(
                        SchoolClasses::getClassName).collect(Collectors.joining(", ")))
                .classCategory(updatedTeacher.getClassCategory().toString())
                .build();
    }

    @Override
    @Transactional
    public void removeTeacherFromClass(Long teacherId, List<Long> classIds) {
        Teachers teachers = teacherRepository.findById(teacherId).orElseThrow(()->
                new TeacherNotFoundException("Teacher with " + teacherId + " not found"));

        Set<SchoolClasses> classesToRemove = new HashSet<>();
        for (Long classId : classIds) {
            SchoolClasses schoolClasses = schoolClassesRepository.findById(classId).orElseThrow(()->
                    new SchoolClassNotFoundException("Class with classId: " + classId + " not found"));

            // Check if the teacher is assigned to the class
            if (schoolClasses.getTeachers().contains(teachers)) {
                // Remove the teacher from the class
                schoolClasses.getTeachers().remove(teachers);
                // Add class to the list of classes to be saved
                classesToRemove.add(schoolClasses);

                // Remove the class from the teacher's list of classes
                teachers.getSchoolClasses().remove(schoolClasses);

                // Decrement the teacher count
                schoolClasses.setTeacherCount(schoolClasses.getTeacherCount() - 1);

            } else {
                throw new IllegalStateException("Teacher with ID: "
                        + teacherId + " is already removed from "
                        + schoolClasses.getClassName());
            }
        }
        // Save the updated teacher entity
        teacherRepository.save(teachers);

        // Save the updated classes
        schoolClassesRepository.saveAll(classesToRemove);
//        schoolClassesRepository.saveAll(classIds.stream()
//                .map(classId -> schoolClassesRepository.findById(classId).get())
//                .collect(Collectors.toList()));
    }

    @Override
    public TeacherResponse assignTeacherToSubjects(Long teacherId, List<Subjects> subjects) {
        Teachers teachers = teacherRepository.findById(teacherId).orElseThrow(()->
                new TeacherNotFoundException("Teacher with " + teacherId + " not found"));

        teachers.getSubjectsList().addAll(subjects);
        Teachers updatedTeacher = teacherRepository.save(teachers);

        String classNames = updatedTeacher.getSchoolClasses().stream()
                .map(SchoolClasses::getClassName) // Ensure `getClassName` returns the class name as a String
                .collect(Collectors.joining(", "));

        return TeacherResponse.builder()
                .firstName(updatedTeacher.getFirstName())
                .lastName(updatedTeacher.getLastName())
                .roles(updatedTeacher.getRoles().toString())
                .teacherType(updatedTeacher.getType().toString())
                .className(classNames)
                .subjects(updatedTeacher.getSubjectsList()
                        .stream()
                        .map(Subjects::name)
                        .collect(Collectors.joining(", ")))
                .build();
    }

    public Optional<TeacherResponse> getTeacherById(Long id){
        Teachers teachers = teacherRepository.findTeacherById(id).orElseThrow(()->
                new TeacherNotFoundException("Teacher with " + id + " not found."));

        return Optional.ofNullable(mapToTeacherResponse(teachers));
    }

    @Override
    public List<TeacherResponse> getAllTeachers() {
        List<Teachers> teachersList = teacherRepository.findAll();

        return teachersList.stream()
                .map(this::mapToTeacherResponse)
                .toList();
    }

    @Override
    public TaskDto createTask(TaskDto taskDto) {
        Optional<SchoolClasses> classes = schoolClassesRepository.findById(taskDto.getClassId());
        if (classes.isPresent()){
            Task task = new Task();
            task.setTitle(taskDto.getTitle());
            task.setDescription(taskDto.getDescription());
            task.setPriority(taskDto.getPriority());
            task.setDueDate(taskDto.getDueDate());
            task.setTaskStatus(TaskStatus.IN_PROGRESS);
            task.setSchoolClasses(classes.get());

            return taskRepository.save(task).getTaskResponse();

        }
        return null;
    }

    @Override
    public List<TaskDto> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Task::getDueDate)
                        .reversed())
                .map(Task::getTaskResponse).toList();
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public TaskDto getTaskById(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        return optionalTask.map(Task::getTaskResponse).orElse(null);
    }

    @Override
    public TaskDto updateTask(Long id, TaskDto taskDto) {
        Optional<Task> updateTask = taskRepository.findById(id);
        //Optional<SchoolClasses> classToUpdateTask = schoolClassesRepository.findById(id);
        if (updateTask.isPresent() ) {
            Task existingTask = updateTask.get();
            existingTask.setTitle(taskDto.getTitle());
            existingTask.setDescription(taskDto.getDescription());
            existingTask.setPriority(taskDto.getPriority());
            existingTask.setDueDate(taskDto.getDueDate());
            existingTask.setTaskStatus(mapStringToTaskStatus(String.valueOf(taskDto.getTaskStatus())));
           // existingTask.setSchoolClasses(classToUpdateTask.get());

            return taskRepository.save(existingTask).getTaskResponse();



        }
        return null;
    }

    @Override
    public List<TaskDto> searchTaskByTitle(String title) {
        return taskRepository.findTaskByTitleContaining(title)
                .stream()
                .sorted(Comparator.comparing(Task::getDueDate).reversed())
                .map(Task::getTaskResponse)
                .toList();
    }

    @Override
    public List<TaskDto> getTaskByTaskClassId(Long classId) {
        List<Task> tasks = taskRepository.findBySchoolClassesId(classId);
        return tasks.stream()
                .map(Task::getTaskResponse)
                .collect(Collectors.toList());
    }

    @Override
    public String deleteTeacher(Long id) {
        Teachers teacher = teacherRepository.findTeacherById(id)
                .orElseThrow(() -> new TeacherNotFoundException("Teacher with ID number: " + id + " not found"));
        // Remove teacher from all associated classes
        for (SchoolClasses schoolClass : teacher.getSchoolClasses()) {
            schoolClass.getTeachers().remove(teacher);

            schoolClass.setTeacherCount(schoolClass.getTeacherCount() - 1);

        }
        teacherRepository.deleteById(teacher.getId());
        return teacher.getFirstName() + " " + teacher.getLastName() + " deleted successfully";
    }

    @Override
    public TeacherResponse updateTeacher(Long id, TeacherRequest teacherRequest) {
        Optional<Teachers> updateTeacher = teacherRepository.findTeacherById(id);
        if (updateTeacher.isPresent()) {
            Teachers existingTeacher = updateTeacher.get();
            existingTeacher.setFirstName(teacherRequest.getFirstName());
            existingTeacher.setLastName(teacherRequest.getLastName());
            existingTeacher.setEmail(teacherRequest.getEmail());
            existingTeacher.setAge(teacherRequest.getAge());
            existingTeacher.setPhoneNumber(teacherRequest.getPhoneNumber());
            existingTeacher.setQualification(teacherRequest.getQualification());
            existingTeacher.setType(teacherRequest.getTeacherType());
            existingTeacher.setRoles(teacherRequest.getRoles());
            existingTeacher.setClassCategory(teacherRequest.getClassCategory());

            Teachers savedTeacher = teacherRepository.save(existingTeacher);

            // Map the saved teacher to TeacherDto and return it;
            return mapToTeacherResponse(savedTeacher);


        }
        throw new TeacherNotFoundException("Teacher with id " + id + " not found.");
    }

    @Override
    public List<StudentResponse> getAllStudentsByClassName(String className) {
        List<Students> students = studentRepository.findBySchoolClasses_ClassName(className);

        return students.stream()
                .map(this::mapToStudentResponse)
                .collect(Collectors.toList());
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

    private TeacherResponse mapToTeacherResponse(Teachers teachers) {
        // Convert the set of SchoolClasses to a set of class names
        Set<String> classNames = teachers.getSchoolClasses()
                .stream()
                .map(SchoolClasses::getClassName)
                .collect(Collectors.toSet());

        return TeacherResponse.builder()
                .firstName(teachers.getFirstName())
                .lastName(teachers.getLastName())
                .email(teachers.getEmail())
                .age(teachers.getAge())
                .phoneNumber(teachers.getPhoneNumber())
                .teacherType(teachers.getType().toString())
                .qualification(teachers.getQualification())
                .roles(teachers.getRoles().toString())
                .className(classNames.toString())
                .classCategory(teachers.getClassCategory().toString())
                .subjects(teachers.getSubjectsList().toString())
                .build();
    }


}
