package com.example.bbm384oyd.controllers;

import com.example.bbm384oyd.model.Admin;
import com.example.bbm384oyd.model.Student;
import com.example.bbm384oyd.model.Instructor;
import com.example.bbm384oyd.model.DepartmentManager;
import com.example.bbm384oyd.repository.AdminRepository;
import com.example.bbm384oyd.repository.StudentRepository;
import com.example.bbm384oyd.repository.InstructorRepository;
import com.example.bbm384oyd.repository.DepartmentManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignUpController {
    
    private final AdminRepository adminRepository;
    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;
    private final DepartmentManagerRepository departmentManagerRepository;

    @Autowired
    public SignUpController(AdminRepository adminRepository, StudentRepository studentRepository,
                           InstructorRepository instructorRepository, DepartmentManagerRepository departmentManagerRepository) {
        this.adminRepository = adminRepository;
        this.studentRepository = studentRepository;
        this.instructorRepository = instructorRepository;
        this.departmentManagerRepository = departmentManagerRepository;
    }

    @PostMapping("/signupAdmin")
    public ResponseEntity<Admin> signupAdmin(@RequestBody Admin admin) {
        if (adminRepository.findByEmail(admin.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Email is already registered
        }
        adminRepository.save(admin);
        return ResponseEntity.ok(admin);
    }

    @PostMapping("/signupStudent")
    public ResponseEntity<Student> signupStudent(@RequestBody Student student) {
        if (studentRepository.findByEmail(student.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Email is already registered
        }
        studentRepository.save(student);
        return ResponseEntity.ok(student);
    }

    @PostMapping("/signupInstructor")
    public ResponseEntity<Instructor> signupInstructor(@RequestBody Instructor instructor) {
        if (instructorRepository.findByEmail(instructor.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Email is already registered
        }
        instructorRepository.save(instructor);
        return ResponseEntity.ok(instructor);
    }

    @PostMapping("/signupDepartmentManager")
    public ResponseEntity<DepartmentManager> signupDepartmentManager(@RequestBody DepartmentManager departmentManager) {
        if (departmentManagerRepository.findByEmail(departmentManager.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Email is already registered
        }
        departmentManagerRepository.save(departmentManager);
        return ResponseEntity.ok(departmentManager);
    }
}
