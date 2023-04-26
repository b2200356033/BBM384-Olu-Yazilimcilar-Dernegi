package com.example.bbm384oyd.controllers;

import org.springframework.web.bind.annotation.*;

import com.example.bbm384oyd.model.Student;

@RestController
@RequestMapping("/student")
public class StudentController {
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
        return savedStudent;
    }
    
    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable("id") Long id, @RequestBody Student student) {
        // update student with given id in database using student object passed in request body

        //Dummy Object
        Student updatedStudent = new Student();
        return updatedStudent;
    }
    
    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable("id") Long id) {
        // delete student with given id from database
    }
}
