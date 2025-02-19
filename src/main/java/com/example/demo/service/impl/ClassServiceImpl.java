package com.example.demo.service.impl;

import com.example.demo.entity.StudentClass;
import com.example.demo.repository.ClassRepository;
import com.example.demo.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    private ClassRepository classRepository;


    @Override
    public List<StudentClass> getAllClasses() {
        return classRepository.findAll();
    }

    @Override
    public Optional<StudentClass> getClassById(Integer id) {
        return classRepository.findById(id);
    }

    @Override
    public StudentClass saveClass(StudentClass classObj) {
        return classRepository.save(classObj);
    }

    @Override
    public void deleteClass(Integer id) {
        classRepository.deleteById(id);
    }

    @Override
    public StudentClass saveClasses(StudentClass classes) {
        return classRepository.save(classes);
    }

    @Override
    public List<StudentClass> filterClasses(Specification<StudentClass> spec) {
        return classRepository.findAll(spec);
    }

//    @Override
//    public List<StudentClass> saveClasses(List<StudentClass> classes) {
//        return classRepository.saveAll(classes);
//    }

}
