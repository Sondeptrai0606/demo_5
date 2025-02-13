package com.example.demo.repository;
import com.example.demo.entity.StudentClass;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRepository extends JpaRepository<StudentClass,Integer> {
}
