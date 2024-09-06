package com.cressy.schoolmanagementsystem.controller.commentController;

import com.cressy.schoolmanagementsystem.dto.CommentDto;
import com.cressy.schoolmanagementsystem.dto.CommentRequestDto;
import com.cressy.schoolmanagementsystem.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/add/student")
    public ResponseEntity<CommentDto> addCommentByStudent(@RequestBody CommentRequestDto commentRequest) {
        CommentDto comment = commentService.addCommentByStudent(commentRequest.getTaskId(),
                commentRequest.getStudentNumber(),
                commentRequest.getContent());
        return ResponseEntity.ok(comment);
    }

    @PostMapping("/add/teacher")
    public ResponseEntity<CommentDto> addCommentByTeacher(@RequestBody CommentRequestDto commentRequest) {
        CommentDto comment = commentService.addCommentByTeacher(commentRequest.getTaskId(), commentRequest.getTeacherId(), commentRequest.getContent());
        return ResponseEntity.ok(comment);
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<CommentDto>> getCommentsByTaskId(@PathVariable Long taskId) {
        List<CommentDto> comments = commentService.getCommentByTaskId(taskId);
        return ResponseEntity.ok(comments);
    }
}
