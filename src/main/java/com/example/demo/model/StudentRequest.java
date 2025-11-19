package com.example.demo.model;

import lombok.Data;
import java.time.LocalDate;

@Data
public class StudentRequest {

    private String fullName;
    private String address;
    private LocalDate dob;
}
