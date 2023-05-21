package com.example.bbm384oyd.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.bbm384oyd.model.Course;
import com.example.bbm384oyd.model.Instructor;
import com.example.bbm384oyd.model.Student;
import com.example.bbm384oyd.repository.CourseRepository;
import com.example.bbm384oyd.repository.InstructorRepository;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private InstructorRepository instructorRepository;
    @Transactional
    public Course getCourseById(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));
    }

    @Transactional
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Transactional
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    @Transactional
    public void updateCourse(Long courseId, Course updatedCourse) {
        Course existingCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        existingCourse.setName(updatedCourse.getName());
        existingCourse.setCredit(updatedCourse.getCredit());
        existingCourse.setDepartment(updatedCourse.getDepartment());
        existingCourse.setType(updatedCourse.getType());
        
        // Set other properties that you want to update

        courseRepository.save(existingCourse);
    }

    @Transactional
    public void deleteCourseById(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        courseRepository.delete(course);
    }
    
    @Transactional
    public List<Student> getStudentsByCourseId(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        return course.getStudents();
    }
    @Transactional
    public void setCourseInstructor(Long courseId, Long instructorId){
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        Instructor instructor = instructorRepository.findById(instructorId).orElseThrow(() -> new IllegalArgumentException("Instructor not found"));

        course.setInstructor(instructor);
        courseRepository.save(course);
    }
}
