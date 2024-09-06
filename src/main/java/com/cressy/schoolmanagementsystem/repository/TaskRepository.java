package com.cressy.schoolmanagementsystem.repository;

import com.cressy.schoolmanagementsystem.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findTaskByTitleContaining(String title);
    List<Task> findBySchoolClassesId(Long classId);

}
