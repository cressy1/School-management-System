package com.cressy.schoolmanagementsystem.dto;

import com.cressy.schoolmanagementsystem.enums.ClassType;
import com.cressy.schoolmanagementsystem.enums.TaskStatus;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private Date dueDate;
    private String priority;
    private TaskStatus taskStatus;
    private Long classId;
    private String className;
    private ClassType classType;

}
