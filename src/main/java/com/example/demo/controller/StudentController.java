package com.example.demo.controller;

import com.example.demo.entity.Student;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // Lấy danh sách tất cả sinh viên
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudent();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    // Lấy sinh viên theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Integer id) {
        return studentService.findStudentById(id)
                .map(student -> new ResponseEntity<>(student, HttpStatus.OK))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
    }

    // Thêm sinh viên mới
    @PostMapping("/add")
    public ResponseEntity<?> createStudent(@RequestBody Student student) {
        if (student.getStudentClass() == null || student.getStudentClass().getId() == 0) {
            return ResponseEntity.badRequest().body("Lỗi: classId không hợp lệ!");
        }
        if (student.getAddress() == null || student.getAddress().getId() == 0) {
            return ResponseEntity.badRequest().body("Lỗi: addressId không hợp lệ!");
        }

        try {
            // Lưu sinh viên
            Student savedStudent = studentService.saveUser(student);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi lưu sinh viên: " + e.getMessage());
        }
    }



    // Cập nhật thông tin sinh viên
    @PutMapping("/update/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Integer id, @RequestBody Student studentDetails) {
        return studentService.findStudentById(id)
                .map(student -> {
                    student.setName(studentDetails.getName());
                    student.setAge(studentDetails.getAge());
                    studentService.saveUser(student);
                    return new ResponseEntity<>(student, HttpStatus.OK);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
    }

    // Xóa sinh viên theo ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Integer id) {
        studentService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Deleted student with ID: " + id);
    }
}
