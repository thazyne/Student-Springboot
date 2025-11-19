package com.example.demo.Service;

import com.example.demo.model.Student;
import com.example.demo.model.StudentEntity;
import com.example.demo.model.StudentRequest;
import com.example.demo.Repositery.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    private static final int NIM_LENGTH = 5;

    /**
     * Mengambil semua data mahasiswa dan mengubah ke DTO.
     */
    public List<Student> getStudents() {
        return studentRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Mengubah entity StudentEntity ke DTO Student.
     */
    private Student convertToDto(StudentEntity entity) {
        Student dto = new Student();
        dto.setNim(entity.getNim());
        dto.setFullName(entity.getFullName());
        dto.setAddress(entity.getAddress());
        dto.setDob(entity.getDob());
        return dto;
    }

    /**
     * Menambah data mahasiswa baru dengan validasi duplikasi nama dan tanggal lahir.
     */
    public Student addStudent(StudentRequest request) {
        if (isDuplicateStudent(request.getFullName(), request.getDob())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data already exists");
        }

        StudentEntity newStudent = new StudentEntity();
        newStudent.setNim(generateNextNim());
        newStudent.setFullName(request.getFullName());
        newStudent.setDob(request.getDob());
        newStudent.setAddress(request.getAddress());

        StudentEntity savedStudent = studentRepository.save(newStudent);
        return convertToDto(savedStudent);
    }

    /**
     * Mengecek duplikasi data mahasiswa berdasarkan nama dan tanggal lahir.
     */
    private boolean isDuplicateStudent(String fullName, java.time.LocalDate dob) {
        return studentRepository.existsByFullNameAndDob(fullName, dob);
    }

    /**
     * Generate NIM berikutnya berdasarkan NIM terbesar yang ada.
     */
    private String generateNextNim() {
        String maxNim = studentRepository.findMaxNim();
        int nextNim = (maxNim == null) ? 1 : Integer.parseInt(maxNim) + 1;
        return String.format("%0" + NIM_LENGTH + "d", nextNim);
    }

    /**
     * Mencari mahasiswa berdasarkan NIM.
     */
    public Student findStudentByNim(String nim) {
        StudentEntity entity = studentRepository.findByNim(nim)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
        return convertToDto(entity);
    }

    /**
     * Menghapus mahasiswa berdasarkan NIM.
     */
    public void deleteStudent(String nim) {
        StudentEntity entity = studentRepository.findByNim(nim)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
        studentRepository.delete(entity);
    }

    /**
     * Mengupdate data mahasiswa berdasarkan NIM.
     */
    public Student updateStudent(String nim, StudentRequest request) {
        StudentEntity entity = studentRepository.findByNim(nim)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));

        entity.setFullName(request.getFullName());
        entity.setDob(request.getDob());
        entity.setAddress(request.getAddress());

        StudentEntity updatedEntity = studentRepository.save(entity);
        return convertToDto(updatedEntity);
    }
}
