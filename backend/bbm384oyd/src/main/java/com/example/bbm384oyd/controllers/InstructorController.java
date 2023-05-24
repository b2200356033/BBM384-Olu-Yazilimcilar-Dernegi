package com.example.bbm384oyd.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bbm384oyd.model.Instructor;
import com.example.bbm384oyd.repository.InstructorRepository;

@RestController
@RequestMapping("/instructor")
public class InstructorController {
    @Autowired
    private InstructorRepository instructorRepository;

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
    
    @DeleteMapping("/{email}")
    public Instructor deleteInstructor(@PathVariable("email") String email) {
        Instructor user = null;
        user = instructorRepository.findByEmail(email);
        if (user != null) {
            instructorRepository.delete(user);
        }
        return user;
    }

    @DeleteMapping("/{name}/{surname}")
    public Instructor deleteInstructor(@PathVariable("name") String name, @PathVariable("surname") String surname) {
        Instructor user = null;
        user = instructorRepository.findByNameAndSurname(name, surname).get(0);
        if (user != null) {
            instructorRepository.delete(user);
        }
        return user;
    }

}
