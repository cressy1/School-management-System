package com.cressy.schoolmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequestDto {
    private Long taskId;
    private String studentNumber;
    private Long teacherId;
    private String content;

}
