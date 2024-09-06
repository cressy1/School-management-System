package com.cressy.schoolmanagementsystem.repository;

import com.cressy.schoolmanagementsystem.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comments, Long> {
    List<Comments> findByTaskId(Long taskId);
}
