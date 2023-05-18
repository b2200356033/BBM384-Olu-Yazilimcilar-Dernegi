package com.example.bbm384oyd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.bbm384oyd.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    


}
