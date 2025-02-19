package com.example.demo.service;

import com.example.demo.entity.Student;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    List<Student> getAllStudents();
    Student saveStudent(Student student);
    void deleteStudent(Integer id);
    Optional<Student> findStudentById(Integer id);
    List<Student> filterStudents(Specification<Student> spec);

}
