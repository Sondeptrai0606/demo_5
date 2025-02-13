package com.example.demo.entity;

import jakarta.persistence.*;
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

    private String name;
    private int age;

    // Quan hệ N-1 với Class
    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude// Khóa ngoại trỏ đến Class
    private StudentClass studentClass;

    // Quan hệ 1-1 với Address
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Address address;

}
