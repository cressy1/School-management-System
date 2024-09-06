package com.cressy.schoolmanagementsystem.entity;

import com.cressy.schoolmanagementsystem.enums.ClassCategory;
import com.cressy.schoolmanagementsystem.enums.Roles;
import com.cressy.schoolmanagementsystem.enums.StudentType;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Students")
public class Students extends BaseEntity{
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String temporaryPassword;
    private int age;
    private String phoneNumber;
    private String studentNumber;
    private String className;
    private String classType;
    private String category;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    private SchoolClasses schoolClasses;


    @Enumerated(EnumType.STRING)
    private Roles roles;


    @Enumerated(EnumType.STRING)
    private ClassCategory classCategory;

    @Enumerated(EnumType.STRING)
    private StudentType studentType;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "student-comments") // Prevents infinite loop during serialization
    private List<Comments> comments = new ArrayList<>();

}
