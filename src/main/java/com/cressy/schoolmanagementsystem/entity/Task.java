package com.cressy.schoolmanagementsystem.entity;

import com.cressy.schoolmanagementsystem.dto.TaskDto;
import com.cressy.schoolmanagementsystem.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Tasks")
public class Task extends BaseEntity{
    private String title;
    private String description;
    private Date dueDate;
    private String priority;
    private TaskStatus taskStatus;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "class_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private SchoolClasses schoolClasses;

    public TaskDto getTaskResponse() {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(getId());
        taskDto.setTitle(title);
        taskDto.setDescription(description);
        taskDto.setClassId(schoolClasses.getId());
        taskDto.setClassName(schoolClasses.getClassName());
        taskDto.setClassType(schoolClasses.getClassType());
        taskDto.setDueDate(dueDate);
        taskDto.setPriority(priority);
        taskDto.setTaskStatus(taskStatus);

        return taskDto;

    }
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "task-comments")
    private List<Comments> comments;

}
