package com.example.bbm384oyd.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.bbm384oyd.model.Course;
import com.example.bbm384oyd.model.Instructor;
import com.example.bbm384oyd.model.Student;
import com.example.bbm384oyd.repository.CourseRepository;
import com.example.bbm384oyd.repository.InstructorRepository;
import com.example.bbm384oyd.repository.StudentRepository;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private InstructorRepository instructorRepository;
    @Autowired
    private StudentRepository studentRepository;
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
    public Course setCourseInstructor(Course courseT){
        Course course = courseRepository.findById(courseT.getId())
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        Instructor instructor = instructorRepository.findById(courseT.getInstructor().getId()).orElseThrow(() -> new IllegalArgumentException("Instructor not found"));

        course.setInstructor(instructor);
        instructor.getCourses().add(course); //If it is not important it can be deleted.
        courseRepository.save(course);
        return course;
    }

    public List<Course> getAllCoursesWithSurveys(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));
        List<Course> courses=courseRepository.findAll();
        List<Course> results= new ArrayList<>();
        for(Course c: courses){
            if(c.getSurvey()!=null && student!=null){
                if(student.getCourses().contains(c)){
                    results.add(c);
                }
                
            }
            
        }
        return results;
    }

    @Transactional
    public Instructor assignInstructorTCourse(Long courseId, Long instructorId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new IllegalArgumentException("Instructor not found"));

        course.setInstructor(instructor);
        instructor.getCourses().add(course); //If it is not important it can be deleted.
        courseRepository.save(course);
        return instructor;
}

}

