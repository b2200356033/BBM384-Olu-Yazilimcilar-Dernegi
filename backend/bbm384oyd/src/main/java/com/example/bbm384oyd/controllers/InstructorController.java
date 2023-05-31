package com.example.bbm384oyd.controllers;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bbm384oyd.controllers.SignUpController.SignupRequest;
import com.example.bbm384oyd.model.Course;
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

    @GetMapping("/{instructorId}/courses")
    public List<Course> getAllCourses(@PathVariable Long instructorId) {
        // retrieve all instructors from database and return them

        return instructorService.getAllCoursesOfInstructor(instructorId);
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
    public ResponseEntity<Instructor> createInstructor(@RequestBody SignupRequest signupRequest) {
        if (!instructorRepository.findByEmail2(signupRequest.getEmail()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Email is already registered
        }
    
        Instructor instructor = new Instructor();
        instructor.setName(signupRequest.getName());
        instructor.setSurname(signupRequest.getSurname());
        instructor.setEmail(signupRequest.getEmail());
        instructor.setPassword(signupRequest.getPassword()); // Remember to hash the password before storing it
        instructor.setPhoto(signupRequest.getPhoto());
    
        instructorRepository.save(instructor);
        return ResponseEntity.ok(instructor);
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

    @GetMapping("/email/{email}")
    public Instructor getInstructor(@PathVariable("email") String email) {
        Instructor user = null;
        List<Instructor> list = instructorRepository.findByEmail2(email);
        if (list.size() != 0) {
            user = list.get(0);
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

    @PutMapping("/manage/email/{email}")
    public Instructor manageInstructorEmail(@PathVariable("email") String oldEmail, @RequestBody String newEmail) {
        newEmail = newEmail.replace("\"", "");
        List<Instructor> list_users = instructorRepository.findByEmail2(oldEmail);
        List<Instructor> list_users2 = instructorRepository.findByEmail2(newEmail);
        Instructor copy_user = new Instructor();
        Instructor existing_user = null;
        if (!list_users.isEmpty()) {
            existing_user = list_users.get(0);
            if (list_users2.isEmpty()) {
                existing_user.setEmail(newEmail);
                instructorRepository.save(existing_user);
            }
            else {
                copy_user.setEmail(oldEmail);
                return copy_user;
            }
        }
        return existing_user;
    }


    @PutMapping("/manage/password/{email}")
    public Instructor manageInstructorPassword(@PathVariable("email") String email, @RequestParam("old") String oldPw, @RequestParam("new") String newPw) {
        newPw = newPw.replace("\"", "");
        oldPw = oldPw.replace("\"", "");
        List<Instructor> list_users = instructorRepository.findByEmail2(email);
        Instructor copy_user = new Instructor();
        Instructor existing_user = null;
        if (!list_users.isEmpty()) {
            existing_user = list_users.get(0);
            if (existing_user.getPassword().equals(oldPw)) {
                existing_user.setPassword(newPw);
                instructorRepository.save(existing_user);
            }
            else {
                copy_user.setPassword(oldPw);
                return copy_user;
            }
        }
        return existing_user;
    }

}
