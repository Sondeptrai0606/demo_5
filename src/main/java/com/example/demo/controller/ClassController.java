package com.example.demo.controller;

import com.example.demo.entity.StudentClass;
import com.example.demo.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
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

    @GetMapping("/filter")
    public ResponseEntity<List<StudentClass>> filterClasses(@RequestParam(required = false) String className,
                                                            @RequestParam(required = false) Integer totalStudents) {
        Specification<StudentClass> spec = (root, query, cb) -> {
            Specification<StudentClass> tempSpec = Specification.where(null);
            if (className != null) {
                tempSpec = tempSpec.and((root1, query1, cb1) -> cb1.like(root1.get("className"), "%" + className + "%"));
            }
            if (totalStudents != null) {
                tempSpec = tempSpec.and((root1, query1, cb1) -> cb1.equal(root1.get("totalStudents"), totalStudents));
            }
            return tempSpec.toPredicate(root, query, cb);
        };
        return ResponseEntity.ok(classService.filterClasses(spec));
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
