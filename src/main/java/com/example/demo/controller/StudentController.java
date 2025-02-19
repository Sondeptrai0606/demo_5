package com.example.demo.controller;

import com.example.demo.entity.Student;
import com.example.demo.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
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
    @GetMapping("/filter")
    public ResponseEntity<List<Student>> filterStudents(@RequestParam(required = false) String name,
                                                        @RequestParam(required = false) Integer age) {
        Specification<Student> spec = (root, query, cb) -> {
            Specification<Student> tempSpec = Specification.where(null);
            if (name != null) {
                tempSpec = tempSpec.and((root1, query1, cb1) -> cb1.like(root1.get("name"), "%" + name + "%"));
            }
            if (age != null) {
                tempSpec = tempSpec.and((root1, query1, cb1) -> cb1.equal(root1.get("age"), age));
            }
            return tempSpec.toPredicate(root, query, cb);
        };
        return ResponseEntity.ok(studentService.filterStudents(spec));
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
