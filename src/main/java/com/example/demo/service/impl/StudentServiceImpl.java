package com.example.demo.service.impl;

import com.example.demo.entity.Address;
import com.example.demo.entity.Student;
import com.example.demo.entity.StudentClass;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.ClassRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private AddressRepository addressRepository;


    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    @Transactional
    public Student saveStudent(Student student) {
        // Kiểm tra classId có hợp lệ không
        if (student.getStudentClass() == null || student.getStudentClass().getId() <= 0) {
            throw new RuntimeException("studentClass ID không hợp lệ!");
        }

        // Kiểm tra addressId có hợp lệ không
        if (student.getAddress() == null || student.getAddress().getId() <= 0) {
            throw new RuntimeException("address ID không hợp lệ!");
        }

        // Kiểm tra class ID có tồn tại không
        Optional<StudentClass> classOpt = classRepository.findById(student.getStudentClass().getId());
        if (!classOpt.isPresent()) {
            throw new RuntimeException("Không tìm thấy lớp học với ID " + student.getStudentClass().getId());
        }

        // Kiểm tra address ID có tồn tại không
        Optional<Address> addressOpt = addressRepository.findById(student.getAddress().getId());
        if (!addressOpt.isPresent()) {
            throw new RuntimeException("Không tìm thấy địa chỉ với ID " + student.getAddress().getId());
        }

        // Gán thực thể đã tồn tại thay vì tạo mới
        student.setStudentClass(classOpt.get());
        student.setAddress(addressOpt.get());

        // Lưu sinh viên vào database
        Student savedStudent = studentRepository.save(student);

        // Cập nhật tổng số sinh viên trong lớp học
        StudentClass studentClass = classOpt.get();
        int total =  studentClass.getStudents().size();
        studentClass.setTotalStudents(total);

//        studentClass.setTotalStudents(total);
//        studentClass.updateTotalStudents();
//        classRepository.save(studentClass);

        return savedStudent;
    }


    @Override
    @Transactional
    public void deleteStudent(Integer id) {
        Optional<Student> studentOpt = studentRepository.findById(id);
        if (!studentOpt.isPresent()) {
            throw new RuntimeException("Không tìm thấy sinh viên với ID " + id);
        }

        Student student = studentOpt.get();
        StudentClass studentClass = student.getStudentClass();

        // Xóa sinh viên
        studentRepository.delete(student);


//        if (studentClass != null) {
//            studentClass.updateTotalStudents();
//            classRepository.save(studentClass);
//        }
    }

    @Override
    public Optional<Student> findStudentById(Integer id) {
        return studentRepository.findById(id);
    }

    @Override
    public List<Student> filterStudents(Specification<Student> spec) {
        return studentRepository.findAll(spec);
    }

}
