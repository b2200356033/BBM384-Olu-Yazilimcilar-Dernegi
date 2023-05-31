package com.example.bbm384oyd.controllers;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.bbm384oyd.model.Course;
import com.example.bbm384oyd.service.CourseService;



@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping("/")
    public List<Course> getAllCourses() {
        // retrieve all courses from database and return them
        return courseService.getAllCourses();
    }
    @GetMapping("/withSurvey/{studentId}")
    public List<Course> getAllCoursesWithSurveys(@PathVariable("studentId") Long id) {
        // retrieve all courses from database and return them
        return courseService.getAllCoursesWithSurveys(id);
    }

    @GetMapping("/{id}")
    public Course getCourse(@PathVariable("id") Long id) {
        return courseService.getCourseById(id);
    }
    
    @PostMapping
    public Course createCourse(@RequestBody Course course) {
        return courseService.createCourse(course);
    }

    @PutMapping("/setInstructor")
    public Course setInstructor(@RequestBody Course course) {
        // save course to database and return saved course with generated id
        return  courseService.setCourseInstructor(course);
    }
    
    @PutMapping("/{id}")
    public void updateCourse(@PathVariable("id") Long id, @RequestBody Course course) {
        courseService.updateCourse(id,course);
    }
    
    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable("id") Long id) {
        courseService.deleteCourseById(id);
        
    }
}
