package com.example.demo.service;

import com.example.demo.entity.StudentClass;
import java.util.List;
import java.util.Optional;

public interface ClassService {
    List<StudentClass> getAllClasses();
    Optional<StudentClass> getClassById(Integer id);
    StudentClass saveClass(StudentClass classObj);
    void deleteClass(Integer id);
    StudentClass saveClasses(StudentClass classes);

}
