package com.cressy.schoolmanagementsystem.services.ServiceImplementations;

import com.cressy.schoolmanagementsystem.dto.SchoolClassRequest;
import com.cressy.schoolmanagementsystem.dto.SchoolClassResponse;
import com.cressy.schoolmanagementsystem.entity.SchoolClasses;
import com.cressy.schoolmanagementsystem.enums.ClassCategory;
import com.cressy.schoolmanagementsystem.enums.Subjects;
import com.cressy.schoolmanagementsystem.exceptions.SchoolClassAlreadyExistsException;
import com.cressy.schoolmanagementsystem.repository.SchoolClassesRepository;
import com.cressy.schoolmanagementsystem.repository.TaskRepository;
import com.cressy.schoolmanagementsystem.services.SchoolClassServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SchoolClassServiceImpl implements SchoolClassServices {

    private final SchoolClassesRepository schoolClassesRepository;
    private final TaskRepository taskRepository;

    @Override
    public SchoolClassResponse createClass(SchoolClassRequest schoolClassRequest) {
        Optional<SchoolClasses> existingClass = schoolClassesRepository.className(schoolClassRequest.getClassName());
        if (existingClass.isPresent()) {
            throw  new SchoolClassAlreadyExistsException("Class with " + schoolClassRequest.getClassName() + " already exists.");
        }
        SchoolClasses newSchoolClasses = SchoolClasses.builder()
                .className(schoolClassRequest.getClassName())
                .classType(schoolClassRequest.getClassType())
                .classCategory(schoolClassRequest.getClassCategory())
                .build();

        assignSubjectsToClass(newSchoolClasses);

        SchoolClasses newClass = schoolClassesRepository.save(newSchoolClasses);

        return SchoolClassResponse.builder()
                .id(newClass.getId())
                .className(newClass.getClassName())
                .classType(newClass.getClassType().toString())
                .classCategory(newClass.getClassCategory().toString())
                .subjects(newClass.getSubjects())
                .numberOfStudentsInClass(newClass.getStudentCount())
                .numberOfTeachersInClass(newClass.getTeacherCount())
                .build();
    }

    @Override
    public void assignSubjectsToClass(SchoolClasses schoolClasses) {
        Set<Subjects> subjects;
        switch (schoolClasses.getClassType()) {
            case JUNIOR_CLASS:
                subjects = EnumSet.of(Subjects.MATHS, Subjects.ENGLISH, Subjects.AGRICULTURAL_SCIENCE, Subjects.BUSINESS_STUDY,
                        Subjects.BUSINESS_STUDY, Subjects.FINE_ART, Subjects.HOME_ECONOMICS);
                break;
            case SENIOR_CLASS:
                subjects = getSeniorClassSubjects(schoolClasses.getClassCategory());
                break;
            default:
                subjects = EnumSet.noneOf(Subjects.class);

        }
        schoolClasses.setSubjects(subjects);

    }
    @Override
    public Set<Subjects> getSeniorClassSubjects(ClassCategory category) {
        switch (category) {
            case SCIENCE :
                return EnumSet.of(Subjects.MATHS, Subjects.ENGLISH, Subjects.BIOLOGY, Subjects.CHEMISTRY, Subjects.PHYSICS, Subjects.AGRICULTURAL_SCIENCE);
            case ART:
                return EnumSet.of(Subjects.MATHS,Subjects.ENGLISH, Subjects.LITERATURE, Subjects.GOVERNMENT, Subjects.HISTORY, Subjects.GEOGRAPHY);
            case COMMERCIAL:
                return EnumSet.of(Subjects.MATHS, Subjects.ENGLISH, Subjects.ACCOUNTING, Subjects.COMMERCE);
            default:
                return EnumSet.noneOf(Subjects.class);
        }

    }

    //find a way to create a method that the student can get a task for his/her particular class by inserting the task id

//    @Override
//    public List<TaskDto> getTaskByClassId(Long id) {
//        Optional<SchoolClasses> schoolClasses = schoolClassesRepository.findById(id);
//        if (schoolClasses.isPresent()){
//            taskRepository.findById(schoolClasses.get().getId()).stream()
//                    .sorted(Comparator.comparing(Task::getDueDate))
//                    .map(Task::getTaskResponse)
//                    .toList();
//
//
//        }
//        return null;
//    }
}
