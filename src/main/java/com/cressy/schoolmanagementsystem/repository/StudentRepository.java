package com.cressy.schoolmanagementsystem.repository;

import com.cressy.schoolmanagementsystem.entity.SchoolClasses;
import com.cressy.schoolmanagementsystem.entity.Students;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Students, Long> {
    Optional<Students> findByEmail(String email);
    Optional<Students> findTopByOrderByStudentNumberDesc();
    Optional<Students> findByStudentNumber(String studentNumber);
    //List<Students> getAllStudentsByClassId(Long classId);
    List<Students> findAll();
    void deleteByStudentNumber(String studentNumber);
    List<Students> findBySchoolClasses_ClassName(String className);

}
