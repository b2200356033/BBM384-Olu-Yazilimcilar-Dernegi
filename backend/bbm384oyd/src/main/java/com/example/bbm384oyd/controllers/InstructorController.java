package com.example.bbm384oyd.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.bbm384oyd.model.Instructor;
import com.example.bbm384oyd.service.InstructorService;

@RestController
@RequestMapping("/instructor")
public class InstructorController {
    
    @Autowired
    private  InstructorService instructorService;

    
    //Iterable or List ??
    @GetMapping("/")
    public List<Instructor> getAllInstructors() {
        // retrieve all instructors from database and return them
        return instructorService.getAllInstructors();
    }
    @GetMapping("/deneme")
    public String deneme() {
        // retrieve all instructors from database and return them
        return "deneme2";
    }
   
    @GetMapping("/{id}")
    public Instructor getInstructorById(@PathVariable("id") Long id) {
        // retrieve instructor by id from database and return it
        return instructorService.getInstructorById(id);
    }
    
    @PostMapping
    public Instructor createInstructor(@RequestBody Instructor instructor) {
        // create instructor in database using instructor object passed in request body and return it
        return instructorService.addInstructor(instructor);
    }
    
    @PutMapping("/{id}")
    public Instructor updateInstructor(@PathVariable("id") Long id, @RequestBody Instructor instructor) {
        // update instructor with given id in database using instructor object passed in request body

        return instructorService.updateInstructor(id,instructor);
    }
    
    @DeleteMapping("/{id}")
    public void deleteInstructor(@PathVariable("id") Long id) {
        // delete instructor from database with given id
        instructorService.deleteInstructorById(id);
    }


}
