package com.cressy.schoolmanagementsystem.services;

import com.cressy.schoolmanagementsystem.dto.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto addCommentByStudent(Long taskId, String studentNumber, String content);
    CommentDto addCommentByTeacher(Long taskId, Long teacherId, String content);
    List<CommentDto> getCommentByTaskId(Long taskId);

}
