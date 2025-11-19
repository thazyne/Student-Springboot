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
        Student createdStudent = studentService.addStudent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }

    @GetMapping("/{nim}")
    public ResponseEntity<Student> getStudentByNim(@PathVariable String nim) {
        Student student = studentService.findStudentByNim(nim);
        return ResponseEntity.ok(student);
    }


    @PutMapping("/{nim}")
    public ResponseEntity<Student> updateStudent(@PathVariable String nim, @RequestBody StudentRequest request) {
        Student updatedStudent = studentService.updateStudent(nim, request);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/{nim}")
    public ResponseEntity<Void> deleteStudent(@PathVariable String nim) {
        studentService.deleteStudent(nim);
        return ResponseEntity.noContent().build();
    }
}
