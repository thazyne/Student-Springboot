package com.example.demo.Service;

import com.example.demo.model.Student;
import com.example.demo.model.StudentEntity;
import com.example.demo.model.StudentRequest;
import com.example.demo.Repositery.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    private static final int LENGTH = 5;

    public List<Student> getStudents() {
        return studentRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private Student mapToDto(StudentEntity entity) {
        Student student = new Student();
        student.setNim(entity.getNim());
        student.setFullName(entity.getFullName());
        student.setAddress(entity.getAddress());
        student.setDob(entity.getDob());
        return student;
    }

    public Student addStudent(StudentRequest request) {
        StudentEntity entity = new StudentEntity();
        entity.setNim(generateNIM());
        entity.setFullName(request.getFullName());
        entity.setDob(request.getDob());
        entity.setAddress(request.getAddress());

        StudentEntity savedEntity = studentRepository.save(entity);
        return mapToDto(savedEntity);
    }

    private String generateNIM() {
        String maxNim = studentRepository.findMaxNim();
        // kalau belum ada data → mulai dari 1
        return (maxNim == null)
                ? String.format("%0" + LENGTH + "d", 1)
                : String.format("%0" + LENGTH + "d", Integer.parseInt(maxNim) + 1);
    }
}
