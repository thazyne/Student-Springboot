package com.example.demo.Repositery;

import com.example.demo.model.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {

    @Query("SELECT MAX(s.nim) FROM StudentEntity s")
    String findMaxNim();

    boolean existsByFullNameAndDob(String fullName, LocalDate dob);
    Optional<StudentEntity> findByNim(String nim);
}
