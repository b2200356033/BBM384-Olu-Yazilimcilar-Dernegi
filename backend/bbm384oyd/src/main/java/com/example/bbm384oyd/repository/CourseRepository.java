package com.example.bbm384oyd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.bbm384oyd.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    //get all courses in the system
    @Query("SELECT * from courses")
    List<Course> getAllCourses();
    //get course with the course id
    @Query("SELECT * from courses C WHERE C.id==?1")
    Course getCourseByID(Long ID);
    //get a student's all courses by the students id
    @Query("SELECT * from courses C WHERE C.student_id==?1")//this studentId in the course table will come from one to many relation
    List<Course> getAllCoursesOfStudent(Long studentID);
}
