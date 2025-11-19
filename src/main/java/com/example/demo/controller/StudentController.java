package com.example.demo.controller;

import com.example.demo.model.Student;
import com.example.demo.model.StudentRequest;
import com.example.demo.Service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody StudentRequest request) {
        Student created = studentService.addStudent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
