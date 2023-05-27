package com.example.bbm384oyd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.bbm384oyd.model.Course;
import com.example.bbm384oyd.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByEmail(String email);

    @Query("SELECT i FROM Student i WHERE i.email = ?1")
    List<Student> findByEmail2(String email);

    @Query("SELECT u FROM Student u WHERE u.name = ?1 AND u.surname = ?2")
    List<Student> findByNameAndSurname(String name, String surname);
    
    @Query("SELECT c FROM Student s JOIN s.courses c WHERE s.id = :studentId")
    List<Course> findCoursesByStudentId(@Param("studentId") Long studentId);
}
