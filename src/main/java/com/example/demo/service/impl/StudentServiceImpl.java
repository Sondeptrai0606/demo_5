package com.example.demo.service.impl;

import com.example.demo.entity.Student;
import com.example.demo.entity.StudentClass;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.ClassRepository;
import com.example.demo.service.StudentService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ClassRepository classRepository;

    @Override
    public List<Student> getAllStudent() {
        try {
            return studentRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy danh sách sinh viên: " + e.getMessage());
        }
    }

@PersistenceContext
private EntityManager entityManager;
    @Transactional
    public Student saveUser(Student student) {
        // Kiểm tra tính hợp lệ của classId và addressId
        if (student.getStudentClass() == null || student.getStudentClass().getId() == 0) {
            throw new RuntimeException("Lỗi: classId không hợp lệ!");
        }
        if (student.getAddress() == null || student.getAddress().getId() == 0) {
            throw new RuntimeException("Lỗi: addressId không hợp lệ!");
        }

        // Kiểm tra nếu Address và StudentClass đã bị detached, nếu có, merge lại chúng
        if (student.getStudentClass().getId() > 0) {
            student.setStudentClass(classRepository.getOne(student.getStudentClass().getId())); // Thực hiện lazy loading
        }

        if (student.getAddress().getId() > 0) {
            student.setAddress(entityManager.merge(student.getAddress())); // Merge lại Address nếu bị detached
        }

        // Lưu sinh viên
        return studentRepository.save(student);
    }


    @Override
    public void deleteUser(Integer id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            StudentClass studentClass = student.getStudentClass();

            studentRepository.delete(student);

            // Cập nhật số lượng sinh viên trong lớp
            if (studentClass != null) {
                studentClass.updateTotalStudents();
                classRepository.save(studentClass);
            }
        }
    }

    @Override
    public Optional<Student> findStudentById(Integer id) {
        return studentRepository.findById(id);// Trả về Optional để dễ xử lý null
    }
}
