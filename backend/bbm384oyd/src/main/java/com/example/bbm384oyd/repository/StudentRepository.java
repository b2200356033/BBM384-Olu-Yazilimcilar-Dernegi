package com.example.bbm384oyd.repository;

import com.example.bbm384oyd.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByEmailAndPassword(String email, String password);
}
