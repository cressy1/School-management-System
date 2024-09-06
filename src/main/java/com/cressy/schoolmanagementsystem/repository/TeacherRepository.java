package com.cressy.schoolmanagementsystem.repository;

import com.cressy.schoolmanagementsystem.entity.Teachers;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teachers, Long> {
    Optional<Teachers> findByEmail(String email);
    Optional<Teachers> findTeacherById(Long teacherId);
}
