package com.example.bbm384oyd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.bbm384oyd.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    //get all courses in the system
    @Query("SELECT c from Course c")
    List<Course> getAllCourses();

    //get course with the course id
    @Query("SELECT c from Course c WHERE c.id = ?1")
    Course getCourseByID(Long ID);


}
