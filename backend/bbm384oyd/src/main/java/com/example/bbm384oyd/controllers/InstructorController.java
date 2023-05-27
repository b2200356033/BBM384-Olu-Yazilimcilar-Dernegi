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
import org.springframework.web.bind.annotation.RestController;

import com.example.bbm384oyd.model.Instructor;
import com.example.bbm384oyd.repository.InstructorRepository;
import com.example.bbm384oyd.service.InstructorService;


@RestController
@RequestMapping("/instructor")
public class InstructorController {

    @Autowired
    private InstructorRepository instructorRepository;
    
    @Autowired
    private  InstructorService instructorService;


    
    //Iterable or List ??
    @GetMapping("/")
    public List<Instructor> getAllInstructors() {
        // retrieve all instructors from database and return them
        return instructorService.getAllInstructors();
    }
    @PostMapping("/all")
    public List<Instructor>  deneme() {
        // retrieve all instructors from database and return them
        return instructorService.getAllInstructors();
    }
   
    @GetMapping("/{id}")
    public Instructor getInstructorById(@PathVariable("id") Long id) {
        // retrieve instructor by id from database and return it
        return instructorService.getInstructorById(id);
    }
    
    @PostMapping
    public Instructor createInstructor(@RequestBody Instructor instructor) {
        return instructorRepository.save(instructor);
    }
    
    @PutMapping("/{id}")
    public Instructor updateInstructor(@PathVariable("id") Long id, @RequestBody Instructor instructor) {
        // update instructor with given id in database using instructor object passed in request body

        return instructorService.updateInstructor(id,instructor);
    }
    

    @DeleteMapping("/email/{email}")
    public Instructor deleteInstructor(@PathVariable("email") String email) {
        Instructor user = null;
        List<Instructor> list = instructorRepository.findByEmail2(email);
        if (list.size() != 0) {
            user = list.get(0);
            instructorRepository.delete(user);
        }
        return user;
    }

    @DeleteMapping("/fullname/{name}/{surname}")
    public Instructor deleteInstructor(@PathVariable("name") String name, @PathVariable("surname") String surname) {
        Instructor user = null;
        List<Instructor> list = instructorRepository.findByNameAndSurname(name, surname);
        if (list.size() != 0) {
            user = list.get(0);
            instructorRepository.delete(user);
        }
        return user;
    }

}
