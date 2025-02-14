package com.example.demo.controller;

import com.example.demo.entity.StudentClass;
import com.example.demo.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/classes")
public class ClassController {

    @Autowired
    private ClassService classService;

    @GetMapping
    public ResponseEntity<List<StudentClass>> getAllClasses() {
        return ResponseEntity.ok(classService.getAllClasses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentClass> getClassById(@PathVariable Integer id) {
        Optional<StudentClass> classOpt = classService.getClassById(id);
        return classOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PostMapping("/add")
    public ResponseEntity<?> createClass(@RequestBody StudentClass studentClass) {
        // Kiểm tra className không được để trống
        if (studentClass.getClassName() == null || studentClass.getClassName().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Lỗi: className không được để trống!");
        }

        // Lưu lớp học vào database
        StudentClass savedClass = classService.saveClass(studentClass);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClass);
    }




    @PutMapping("/update/{id}")
    public ResponseEntity<StudentClass> updateClass(@PathVariable Integer id, @RequestBody StudentClass classDetails) {
        Optional<StudentClass> classOpt = classService.getClassById(id);
        if (classOpt.isPresent()) {
            StudentClass classObj = classOpt.get();
            classObj.setClassName(classDetails.getClassName());
            classObj.setTotalStudents(classDetails.getTotalStudents());
            return ResponseEntity.ok(classService.saveClass(classObj));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteClass(@PathVariable Integer id) {
        classService.deleteClass(id);
        return ResponseEntity.ok("Deleted class with ID: " + id);
    }
}
