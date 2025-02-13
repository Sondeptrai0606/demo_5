package com.example.demo.entity;

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

    @Column(nullable = false,name = "Nameofclass")  // Đảm bảo không để trống className
    private String className;

    private int totalStudents = 0; // Mặc định là 0 tránh lỗi null

    @OneToMany(mappedBy = "studentClass", cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
    @ToString.Exclude
    private List<Student> students;

    public void updateTotalStudents() {
        this.totalStudents = (students != null) ? students.size() : 0;
    }
}
