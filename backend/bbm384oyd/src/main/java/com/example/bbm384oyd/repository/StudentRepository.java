package com.example.bbm384oyd.repository;

import com.example.bbm384oyd.model.Student;
import com.example.bbm384oyd.model.Course;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByEmail(String email);
    //to get all students in the system

    @Query("Select * FROM students")
    List<Student> getAllStudents();

    //to get a student with ID
    @Query("Select * FROM students S WHERE S.id==?1")
    Student findByID(Long ID);
    
}
