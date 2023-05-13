package com.example.bbm384oyd.repository;

import com.example.bbm384oyd.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    Instructor findByEmail(String email);
}
