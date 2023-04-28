package com.example.bbm384oyd.controllers;

import org.springframework.web.bind.annotation.*;

import com.example.bbm384oyd.Model.Instructor;

@RestController
@RequestMapping("/instructor")
public class InstructorController {
    @GetMapping("/{id}")
    public Instructor getInstructor(@PathVariable("id") Long id) {
        // retrieve instructor with given id from database


        //Dummy object
        Instructor instructor = new Instructor();
        
        return instructor;
    }
    
    @PostMapping
    public Instructor createInstructor(@RequestBody Instructor instructor) {
        // save instructor to database and return saved instructor with generated id

        //Dummy object
        Instructor savedInstructor = new Instructor();
        return savedInstructor;
    }
    
    @PutMapping("/{id}")
    public Instructor updateInstructor(@PathVariable("id") Long id, @RequestBody Instructor instructor) {
        // update instructor with given id in database using instructor object passed in request body

        //Dummy object
        Instructor updatedInstructor = new Instructor();
        return updatedInstructor;
    }
    
    @DeleteMapping("/{id}")
    public void deleteInstructor(@PathVariable("id") Long id) {

        
    }


}
