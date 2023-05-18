package com.example.bbm384oyd.repository;

import com.example.bbm384oyd.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByEmail(String email);

    @Query("SELECT s FROM Student s")
    List<Student> getAllStudents();

    @Query("SELECT s FROM Student s WHERE s.id = ?1")
    Student findByID(Long ID);
}
