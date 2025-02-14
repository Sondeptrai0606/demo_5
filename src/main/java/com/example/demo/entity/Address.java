package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String streetName;  // Tên đường
    private String district;    // Quận/Huyện
    private String province;    // Tỉnh/Thành phố

    // Quan hệ 1-1 với Student, một sinh viên chỉ có một địa chỉ
    @OneToOne(mappedBy = "address")
    @JsonBackReference // Tránh lỗi vòng lặp khi serialize JSON
    private Student student;
}
