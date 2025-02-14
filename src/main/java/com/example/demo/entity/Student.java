package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Name is mandatory")
    private String name;
    @Min(value=18, message = " Sinh viên phải trên 18 tuôi")
    private int age;

    // Quan hệ N-1: Mỗi sinh viên thuộc một lớp học
    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    @JsonBackReference // Tránh lỗi vòng lặp
    private StudentClass studentClass;

    // Quan hệ 1-1: Mỗi sinh viên có một địa chỉ
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;
}
