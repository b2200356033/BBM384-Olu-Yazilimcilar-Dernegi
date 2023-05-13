
package com.example.bbm384oyd.controllers;

import com.example.bbm384oyd.model.Admin;
import com.example.bbm384oyd.model.Student;
import com.example.bbm384oyd.model.Instructor;
import com.example.bbm384oyd.model.DepartmentManager;
import com.example.bbm384oyd.repository.AdminRepository;
import com.example.bbm384oyd.repository.StudentRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import com.example.bbm384oyd.repository.InstructorRepository;
import com.example.bbm384oyd.repository.DepartmentManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class LoginController {

    private final AdminRepository adminRepository;
    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;
    private final DepartmentManagerRepository departmentManagerRepository;

    @Autowired
    public LoginController(AdminRepository adminRepository, StudentRepository studentRepository,
                           InstructorRepository instructorRepository, DepartmentManagerRepository departmentManagerRepository) {
        this.adminRepository = adminRepository;
        this.studentRepository = studentRepository;
        this.instructorRepository = instructorRepository;
        this.departmentManagerRepository = departmentManagerRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Admin admin = adminRepository.findByEmail(email);
        if (admin != null && admin.getPassword().equals(password)) {
            return ResponseEntity.ok(generateToken(email, "Admin"));
        }

        Student student = studentRepository.findByEmail(email);
        if (student != null && student.getPassword().equals(password)) {
            return ResponseEntity.ok(generateToken(email, "Student"));
        }

        Instructor instructor = instructorRepository.findByEmail(email);
        if (instructor != null && instructor.getPassword().equals(password)) {
            return ResponseEntity.ok(generateToken(email, "Instructor"));
        }

        DepartmentManager departmentManager = departmentManagerRepository.findByEmail(email);
        if (departmentManager != null && departmentManager.getPassword().equals(password)) {
            return ResponseEntity.ok(generateToken(email, "DepartmentManager"));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    private String generateToken(String email, String userType) {
        return Jwts.builder()
            .setSubject(email)
            .claim("userType", userType)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour
            .signWith(SignatureAlgorithm.HS256, "oydsecret")
            .compact();
    }

    public static class LoginRequest {
        private String email;
        private String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
