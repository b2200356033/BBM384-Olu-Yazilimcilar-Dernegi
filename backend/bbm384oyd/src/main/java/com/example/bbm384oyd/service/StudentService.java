package com.example.bbm384oyd.service;

import org.springframework.stereotype.Service;

import com.example.bbm384oyd.model.Course;
import com.example.bbm384oyd.model.Student;
import com.example.bbm384oyd.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

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
}