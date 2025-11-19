package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "student")
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nim", nullable = false, unique = true)
    private String nim;

    @Column(name = "fullname", nullable = false)
    private String fullName;

    @Column(name = "address")
    private String address;

    @Column(name = "dob")
    private LocalDate dob;
}
