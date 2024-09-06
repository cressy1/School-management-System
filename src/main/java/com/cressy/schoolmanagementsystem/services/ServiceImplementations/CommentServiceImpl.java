package com.cressy.schoolmanagementsystem.services.ServiceImplementations;

import com.cressy.schoolmanagementsystem.dto.CommentDto;
import com.cressy.schoolmanagementsystem.entity.*;
import com.cressy.schoolmanagementsystem.exceptions.StudentNotFoundException;
import com.cressy.schoolmanagementsystem.exceptions.TaskNotFoundException;
import com.cressy.schoolmanagementsystem.exceptions.TeacherNotFoundException;
import com.cressy.schoolmanagementsystem.repository.CommentRepository;
import com.cressy.schoolmanagementsystem.repository.StudentRepository;
import com.cressy.schoolmanagementsystem.repository.TaskRepository;
import com.cressy.schoolmanagementsystem.repository.TeacherRepository;
import com.cressy.schoolmanagementsystem.services.CommentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final TaskRepository taskRepository;
    @Override
    public CommentDto addCommentByStudent(Long taskId, String studentNumber, String content) {
        Task task = taskRepository.findById(taskId).orElseThrow(()-> new TaskNotFoundException("Task not found."));
        Students students = studentRepository.findByStudentNumber(studentNumber).orElseThrow(()-> new StudentNotFoundException("Student not found."));
        Comments comments = new Comments();
        comments.setContent(content);
        comments.setStudent(students);
        comments.setTask(task);

        Comments savedComment = commentRepository.save(comments);

        // Convert entity to DTO
        CommentDto commentDto = new CommentDto();
        commentDto.setContent(savedComment.getContent());
        commentDto.setLocalDateTime(savedComment.getCreatedDate());
        commentDto.setPostedBy(students.getFirstName() + " " + students.getLastName());

        return commentDto;
    }

    @Override
    public CommentDto addCommentByTeacher(Long taskId, Long teacherId, String content) {
        Task task = taskRepository.findById(taskId).orElseThrow(()-> new TaskNotFoundException("Task not found."));
        Teachers teachers = teacherRepository.findTeacherById(teacherId).orElseThrow(()-> new TeacherNotFoundException("Teacher not found."));
        Comments comments = new Comments();
        comments.setContent(content);
        comments.setTeacher(teachers);
        comments.setTask(task);

        Comments savedComment = commentRepository.save(comments);

        // Convert entity to DTO
        CommentDto commentDto = new CommentDto();
        commentDto.setContent(savedComment.getContent());
        commentDto.setLocalDateTime(savedComment.getCreatedDate());
        commentDto.setPostedBy(teachers.getFirstName() + " " + teachers.getLastName());

        return commentDto;

    }

    @Override
    public List<CommentDto> getCommentByTaskId(Long taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new TaskNotFoundException("Task not found");
        }

        List<Comments> comments = commentRepository.findByTaskId(taskId);
        return comments.stream().map(comment -> {
            CommentDto dto = new CommentDto();
            dto.setContent(comment.getContent());
            if (comment.getStudent() != null) {
                dto.setPostedBy(comment.getStudent().getFirstName() + " " + comment.getStudent().getLastName());
            } else if (comment.getTeacher() != null) {
                dto.setPostedBy(comment.getTeacher().getFirstName() + " " + comment.getTeacher().getLastName());
            }
            return dto;
        }).toList();
    }
}
