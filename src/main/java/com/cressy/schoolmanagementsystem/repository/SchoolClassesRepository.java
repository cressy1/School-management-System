package com.cressy.schoolmanagementsystem.repository;

import com.cressy.schoolmanagementsystem.entity.SchoolClasses;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SchoolClassesRepository extends JpaRepository<SchoolClasses, Long> {
    Optional<SchoolClasses> className(String className);

}
