package com.cressy.schoolmanagementsystem.entity;

import com.cressy.schoolmanagementsystem.enums.ClassCategory;
import com.cressy.schoolmanagementsystem.enums.ClassType;
import com.cressy.schoolmanagementsystem.enums.Subjects;
import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "classes")
public class SchoolClasses extends BaseEntity{
    private String className;
    private int studentCount;
    private int teacherCount;

    @Enumerated(EnumType.STRING)
    private ClassType classType;

    @Enumerated(EnumType.STRING)
    private ClassCategory classCategory;

    @OneToMany(mappedBy = "schoolClasses", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private Set<Students> students;

    @ElementCollection(targetClass = Subjects.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "class_subject", joinColumns = @JoinColumn(name = "student_id"))
    @Column(name = "subject")
    private Set<Subjects> subjects;

    @ManyToMany(mappedBy = "schoolClasses")
    private Set<Teachers> teachers = new HashSet<>();
}
