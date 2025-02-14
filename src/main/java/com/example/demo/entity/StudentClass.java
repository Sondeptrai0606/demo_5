package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "classes")
public class StudentClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String className; // Tên lớp học
    private int totalStudents = 0; // Số lượng sinh viên trong lớp

    // Quan hệ 1-N: Một lớp có nhiều sinh viên
    @OneToMany(mappedBy = "studentClass", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Tránh lỗi vòng lặp khi serialize JSON
    private List<Student> students;

    public void setTotalStudents(int totalStudents) {
        this.totalStudents = students != null ? totalStudents : 0;
    }

}
