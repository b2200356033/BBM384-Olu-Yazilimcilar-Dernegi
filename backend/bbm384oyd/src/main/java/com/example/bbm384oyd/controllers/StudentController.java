package com.example.bbm384oyd.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bbm384oyd.model.Course;
import com.example.bbm384oyd.model.Student;
import com.example.bbm384oyd.repository.StudentRepository;
import com.example.bbm384oyd.service.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable("id") Long id) {
        // retrieve student with given id from database


        //Dummy Object
        Student student = new Student();
        
        return student;
    }
    
    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        // save student to database and return saved student with generated id

        //Dummy Object
        Student savedStudent = new Student();
        System.out.println("post req arrived");
        System.out.println(student);
        return savedStudent;
    }
    
    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable("id") Long id, @RequestBody Student student) {
        // update student with given id in database using student object passed in request body

        //Dummy Object
        studentService.updateStudent(id,student);
        return student;
    }
    
    @GetMapping("/{id}/courses")
    public List<Course> getCoursesByStudentId(@PathVariable Long id) {
        List<Course> courses = studentRepository.findCoursesByStudentId(id);
        // Further processing or returning the courses
        return courses;
    }
    @PutMapping("/{studentId}/courses/{courseId}")
    public ResponseEntity<String> dropCourse(@PathVariable Long studentId, @PathVariable Long courseId) {
        // Retrieve the course from the database using the courseId if needed

        // Call the dropCourse method in the service
        studentService.dropCourse(studentId, courseId);

        return ResponseEntity.ok("Course dropped successfully");
    }

    @DeleteMapping("/email/{email}")
    public Student deleteStudent(@PathVariable("email") String email) {
        List<Student> list = studentRepository.findByEmail2(email);
        Student user = null;
        if (list.size() != 0) {
            user = list.get(0);
            studentRepository.delete(user);
            return user;
        }
        return user;
    }

    @DeleteMapping("/fullname/{name}/{surname}")
    public Student deleteStudent(@PathVariable("name") String name, @PathVariable("surname") String surname) {
        List <Student> list = studentRepository.findByNameAndSurname(name, surname);
        Student user = null;
        System.out.println("STUDENT");
        if (list.size() != 0) {
            user = list.get(0);
            studentRepository.delete(user);
        }
        return user;
    }
}
