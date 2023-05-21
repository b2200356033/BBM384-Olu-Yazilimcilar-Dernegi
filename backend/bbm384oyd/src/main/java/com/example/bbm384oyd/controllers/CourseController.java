package com.example.bbm384oyd.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.bbm384oyd.model.Course;
import com.example.bbm384oyd.service.CourseService;



@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;


    @GetMapping("/{id}")
    public Course getCourse(@PathVariable("id") Long id) {
        
        return courseService.getCourseById(id);
    }
    
    @PostMapping
    public Course createCourse(@RequestBody Course course) {
        // save course to database and return saved course with generated id
        
        return courseService.createCourse(course);
    }
    
    @PutMapping("/{id}")
    public void updateCourse(@PathVariable("id") Long id, @RequestBody Course course) {
        // update course with given id in database using course object passed in request body
        courseService.updateCourse(id,course);
    }
    
    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable("id") Long id) {
        // delete course with given id from database
        courseService.deleteCourseById(id);
        
    }
}
