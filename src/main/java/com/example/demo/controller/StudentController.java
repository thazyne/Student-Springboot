
package com.example.demo.controller;

import com.example.demo.domain.Student;
import com.example.demo.domain.StudentRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/students")
public class StudentController {
    private List<Student> students = new ArrayList<>();

    
    @GetMapping("/{nim}")
    public ResponseEntity<Student> getStudentByNim(@PathVariable String nim) {
        for (Student student : students) {
            if (student.getNim().equals(nim)) {
                return ResponseEntity.ok(student);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(students);
    }

    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody StudentRequest studentRequest) {
        String nim = UUID.randomUUID().toString().substring(0, 8);
        Student student = new Student();
        student.setNim(nim);
        student.setFullName(studentRequest.getFullName());
        student.setDob(studentRequest.getDob());
        student.setAddress(studentRequest.getAddress());
        students.add(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(student);
    }

   
    @PutMapping("/{nim}")
    public ResponseEntity<Student> updateStudent(@PathVariable String nim, @RequestBody StudentRequest studentRequest) {
        for (Student student : students) {
            if (student.getNim().equals(nim)) {
                student.setFullName(studentRequest.getFullName());
                student.setDob(studentRequest.getDob());
                student.setAddress(studentRequest.getAddress());
                return ResponseEntity.ok(student);
            }
        }
        return ResponseEntity.notFound().build();
    }

   
    @DeleteMapping("/{nim}")
    public ResponseEntity<Void> deleteStudent(@PathVariable String nim) {
        if (nim == null || nim.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        boolean removed = students.removeIf(student -> student.getNim().equals(nim));
        if (removed) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
