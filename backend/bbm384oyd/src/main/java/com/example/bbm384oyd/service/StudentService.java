package com.example.bbm384oyd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bbm384oyd.model.Course;
import com.example.bbm384oyd.model.Student;
import com.example.bbm384oyd.repository.StudentRepository;

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

    @Transactional
    public Student banStudentbyEmail(String email) {
        List<Student> existingStudents = studentRepository.findByEmail2(email);
        Student existingStudent = null;
        if (!existingStudents.isEmpty()) {
            existingStudent = existingStudents.get(0);
            if (existingStudent.getBanned().equals("No")) {
                existingStudent.setBanned("Yes");
                studentRepository.save(existingStudent);
                existingStudent.setBanned("No");
                return existingStudent;
            }
            return existingStudent;
        }
        return existingStudent;
    }

    @Transactional
    public Student banStudentbyFullname (String name, String surname) {
        List<Student> existingStudents = studentRepository.findByNameAndSurname(name, surname);
        Student existingStudent = null;
        Student copyStudent = new Student();
        if (!existingStudents.isEmpty()) {
            existingStudent = existingStudents.get(0);
            copyStudent.setBanned("Yes");
            if (existingStudent.getBanned().equals("No")) {
                copyStudent.setBanned("No");
                existingStudent.setBanned("Yes");
                studentRepository.save(existingStudent);
                return copyStudent;
            }
            return copyStudent;
        }
        return existingStudent;
    }
}
