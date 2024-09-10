package com.cressy.schoolmanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "comments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comments extends BaseEntity{
    private String content;
   // private String postedBy; // Name of the user who posted the comment
    @ManyToOne
    @JoinColumn(name = "student_id")
    @JsonBackReference(value = "student-comments")
    private Students student;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    @JsonBackReference(value = "teacher-comments")
    private Teachers teachers;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    @JsonBackReference(value = "task-comments")
    private Task task;


}
