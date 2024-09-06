package com.cressy.schoolmanagementsystem.entity;

import com.cressy.schoolmanagementsystem.enums.ClassCategory;
import com.cressy.schoolmanagementsystem.enums.Roles;
import com.cressy.schoolmanagementsystem.enums.Subjects;
import com.cressy.schoolmanagementsystem.enums.TeacherType;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Teacher")
public class Teachers extends BaseEntity{
    private String firstName;
    private String lastName;
    private String email;
    private String temporaryPassword;
    private int age;
    private String phoneNumber;
    private String qualification;

    @Enumerated(EnumType.STRING)
    private TeacherType type;

    @Enumerated(EnumType.STRING)
    private Roles roles;

    @Enumerated(EnumType.STRING)
    private ClassCategory classCategory;

    @ManyToMany
    @JoinTable(
            name = "teacher_school_classes",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "school_class_id")
    )
    private Set<SchoolClasses> schoolClasses = new HashSet<>();

    @ElementCollection(targetClass = Subjects.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "teacher_subjects", joinColumns = @JoinColumn(name = "teacher_id"))
    @Column(name = "subject")
    private Set<Subjects> subjectsList = new HashSet<>();

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "teacher-comments") // Prevents infinite loop during serialization
    private List<Comments> comments = new ArrayList<>();
}
