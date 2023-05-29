package com.example.bbm384oyd.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        return studentRepository.save(student);
    }
    
    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable("id") Long id, @RequestBody Student student) {
        // update student with given id in database using student object passed in request body

        //Dummy Object
        studentService.updateStudent(id,student);
        return student;
    }
    
    @GetMapping("/{studentId}/courses")
    public List<Course> getCoursesByStudentId(@PathVariable Long studentId) {
        return studentService.getCoursesByStudentId(studentId);
    }

    @PostMapping("/{studentId}/courses/{courseId}")
    public void addCourseToStudent(@PathVariable Long studentId, @PathVariable Long courseId) {
        studentService.addCourseToStudent(studentId, courseId);
    }

    @DeleteMapping("/{studentId}/courses/{courseId}")
    public void deleteCourseFromStudent(@PathVariable Long studentId, @PathVariable Long courseId) {
        studentService.deleteCourseFromStudent(studentId, courseId);
    }

    @DeleteMapping("/email/{email}")
    public Student deleteStudent(@PathVariable("email") String email) {
        List<Student> list = studentRepository.findByEmail2(email);
        Student user = null;
        if (list.size() != 0) {
            user = list.get(0);
            studentRepository.delete(user);
        }
        return user;
    }

    @DeleteMapping("/fullname/{name}/{surname}")
    public Student deleteStudent(@PathVariable("name") String name, @PathVariable("surname") String surname) {
        List <Student> list = studentRepository.findByNameAndSurname(name, surname);
        Student user = null;
        if (list.size() != 0) {
            user = list.get(0);
            studentRepository.delete(user);
        }
        return user;
    }

    @PutMapping("/ban/email/{email}")
    public Student updateStudent(@PathVariable("email") String email) {
        return studentService.banStudentbyEmail(email);
    }

    @PutMapping("/ban/fullname/{name}/{surname}")
    public Student updateStudent(@PathVariable("name") String name, @PathVariable("surname") String surname) {
        return studentService.banStudentbyFullname(name, surname);
    }

    @PutMapping("/manage/email/{email}")
    public Student manageStudentEmail(@PathVariable("email") String oldEmail, @RequestBody String newEmail) {
        newEmail = newEmail.replace("\"", "");
        List<Student> list_users = studentRepository.findByEmail2(oldEmail);
        List<Student> list_users2 = studentRepository.findByEmail2(newEmail);
        Student copy_user = new Student();
        Student existing_user = null;
        if (!list_users.isEmpty()) {
            existing_user = list_users.get(0);
            if (list_users2.isEmpty()) {
                existing_user.setEmail(newEmail);
                studentRepository.save(existing_user);
            }
            else {
                copy_user.setEmail(oldEmail);
                return copy_user;
            }
        }
        return existing_user;
    }

    @PutMapping("/manage/password/{email}")
    public Student manageStudentPassword(@PathVariable("email") String email, @RequestParam("old") String oldPw, @RequestParam("new") String newPw) {
        System.out.println("Student");
        newPw = newPw.replace("\"", "");
        oldPw = oldPw.replace("\"", "");
        List<Student> list_users = studentRepository.findByEmail2(email);
        Student copy_user = new Student();
        Student existing_user = null;
        if (!list_users.isEmpty()) {
            existing_user = list_users.get(0);
            if (existing_user.getPassword().equals(oldPw)) {
                existing_user.setPassword(newPw);
                studentRepository.save(existing_user);
            }
            else {
                copy_user.setPassword(oldPw);
                return copy_user;
            }
        }
        return existing_user;
    }
}
