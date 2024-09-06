package com.cressy.schoolmanagementsystem.dto;

import lombok.*;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CommentDto {
    private String content;
    private LocalDateTime localDateTime;
    private String postedBy;

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = LocalDateTime.now();
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
}
