package com.example.demo.controller;

import com.example.demo.entity.Student;
import com.example.demo.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // API lấy danh sách sinh viên
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    // API lấy thông tin sinh viên theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Integer id) {
        Optional<Student> studentOpt = studentService.findStudentById(id);
        return studentOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // API thêm sinh viên mới
    @PostMapping("/add")
    public ResponseEntity<Student> createStudent(@RequestBody @Valid Student student) {
        return ResponseEntity.ok(studentService.saveStudent(student));
    }

    // API cập nhật sinh viên
    @PutMapping("/update/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Integer id, @RequestBody Student studentDetails) {
        Optional<Student> studentOpt = studentService.findStudentById(id);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            student.setName(studentDetails.getName());
            student.setAge(studentDetails.getAge());
            return ResponseEntity.ok(studentService.saveStudent(student));
        }
        return ResponseEntity.notFound().build();
    }

    // API xóa sinh viên
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Integer id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok("Deleted student with ID: " + id);
    }
}
