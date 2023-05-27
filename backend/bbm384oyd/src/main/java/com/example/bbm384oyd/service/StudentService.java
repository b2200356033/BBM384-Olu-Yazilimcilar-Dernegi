package com.example.bbm384oyd.service;

import org.springframework.stereotype.Service;

import com.example.bbm384oyd.model.Course;
import com.example.bbm384oyd.model.Student;
import com.example.bbm384oyd.repository.CourseRepository;
import com.example.bbm384oyd.repository.StudentRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;

    @Transactional
    public void updateStudent(Long studentId, Student updatedStudent) {
        Student existingStudent = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        existingStudent.setName(updatedStudent.getName());
        existingStudent.setSurname(updatedStudent.getSurname());
        existingStudent.setEmail(updatedStudent.getEmail());
        existingStudent.setPassword(updatedStudent.getPassword());
        existingStudent.setPhoto(updatedStudent.getPhoto());
        // Set other properties that you want to update

        studentRepository.save(existingStudent);
    }

    @Transactional
    public void deleteStudentById(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        studentRepository.delete(student);
    }

    @Transactional
    public void dropCourse(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        Course course = student.getCourses().stream()
                .filter(c -> c.getId().equals(courseId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Course not found for the student"));

        student.getCourses().remove(course);

        studentRepository.save(student);
    }

    @Transactional
    public List<Course> getCoursesByStudentId(Long studentId) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        System.out.println("Student id searched is:"+studentId);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            System.out.println("Student"+student+"with id:"+studentId+" is found, returning his courses");
            for(Course c:student.getCourses()){
                System.out.println(c);
            }
            return student.getCourses();
        }
        System.out.println("No student found");
        return Collections.emptyList();
    }

    @Transactional
    public void addCourseToStudent(Long studentId, Long courseId) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        Optional<Course> optionalCourse = courseRepository.findById(courseId);

        if (optionalStudent.isPresent() && optionalCourse.isPresent()) {
            Student student = optionalStudent.get();
            Course course = optionalCourse.get();

            student.addCourse(course);
        }
    }

    @Transactional
    public void deleteCourseFromStudent(Long studentId, Long courseId) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        Optional<Course> optionalCourse = courseRepository.findById(courseId);

        if (optionalStudent.isPresent() && optionalCourse.isPresent()) {
            Student student = optionalStudent.get();
            Course course = optionalCourse.get();

            student.dropCourse(course);
        }
    }
}