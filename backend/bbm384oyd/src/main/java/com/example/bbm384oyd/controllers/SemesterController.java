package com.example.bbm384oyd.controllers;

import org.springframework.web.bind.annotation.*;

import com.example.bbm384oyd.model.Semester;

@RestController
@RequestMapping("/semester")
public class SemesterController {
    @GetMapping("/{id}")
    public Semester getSemester(@PathVariable("id") Long id) {
        // retrieve semester with given id from database

        //Dummy Object
        Semester semester = new Semester();
        return semester;
    }
    
    @PostMapping
    public Semester createSemester(@RequestBody Semester semester) {
        // save semester to database and return saved semester with generated id

        //Dummy Object
        Semester savedSemester = new Semester();
        return savedSemester;
    }
    
    @PutMapping("/{id}")
    public Semester updateSemester(@PathVariable("id") Long id, @RequestBody Semester semester) {
        // update semester with given id in database using semester object passed in request body

        //Dummy Object
        Semester updatedSemester = new Semester();
        return updatedSemester;
    }
    
    @DeleteMapping("/{id}")
    public void deleteSemester(@PathVariable("id") Long id) {
        // delete semester with given id from database
    }
}
