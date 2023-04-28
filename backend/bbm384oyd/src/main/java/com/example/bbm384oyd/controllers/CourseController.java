package com.example.bbm384oyd.controllers;
import org.springframework.web.bind.annotation.*;
import com.example.bbm384oyd.model.Course;



@RestController
@RequestMapping("/course")
public class CourseController {
    @GetMapping("/{id}")
    public Course getCourse(@PathVariable("id") Long id) {
        // retrieve course with given id from database

        //Dummy object
        Course course = new Course();

        return course;
    }
    
    @PostMapping
    public Course createCourse(@RequestBody Course course) {
        // save course to database and return saved course with generated id
        
        System.out.println("post req arrived");
        System.out.println(course);
        //Dummy object
        Course savedCourse = new Course();
        return savedCourse;
    }
    
    @PutMapping("/{id}")
    public Course updateCourse(@PathVariable("id") Long id, @RequestBody Course course) {
        // update course with given id in database using course object passed in request body

        //Dummy object
        Course updatedCourse = new Course();
        return updatedCourse;
    }
    
    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable("id") Long id) {
        // delete course with given id from database
        
    }
}
