package com.example.demo.service;

import com.example.demo.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    List<Student> getAllStudent();

    Student saveUser(Student student);  // Đổi từ void thành Student

    void deleteUser(Integer id);

    Optional<Student> findStudentById(Integer id);
}
