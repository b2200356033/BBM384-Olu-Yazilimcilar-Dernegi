package com.example.bbm384oyd.controllers;

import org.springframework.web.bind.annotation.*;

import com.example.bbm384oyd.model.Student;

@RestController
@RequestMapping("/student")
public class StudentController {
    @GetMapping("/{id}")
    public Student getStudent(@PathVariable("id") Long id) {
        // retrieve student with given id from database
        return student;
    }
    
    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        // save student to database and return saved student with generated id
        return savedStudent;
    }
    
    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable("id") Long id, @RequestBody Student student) {
        // update student with given id in database using student object passed in request body
        return updatedStudent;
    }
    
    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable("id") Long id) {
        // delete student with given id from database
    }
}
