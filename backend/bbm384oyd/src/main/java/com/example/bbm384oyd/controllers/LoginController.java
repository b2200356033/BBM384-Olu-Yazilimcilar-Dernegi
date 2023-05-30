package com.example.bbm384oyd.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.bbm384oyd.model.Admin;
import com.example.bbm384oyd.model.DepartmentManager;
import com.example.bbm384oyd.model.Instructor;
import com.example.bbm384oyd.model.Student;
import com.example.bbm384oyd.repository.AdminRepository;
import com.example.bbm384oyd.repository.DepartmentManagerRepository;
import com.example.bbm384oyd.repository.InstructorRepository;
import com.example.bbm384oyd.repository.StudentRepository;


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
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Optional<Admin> admin = adminRepository.findByEmail(email);
        if (admin.isPresent() && admin.get().getPassword().equals(password)) {
            return ResponseEntity.ok(new LoginResponse(admin.get().getId(),"Admin", admin.get().getName(), admin.get().getSurname(), admin.get().getEmail(), admin.get().getPhoto()));
        }

        Student student = studentRepository.findByEmail(email);
        if (student != null  && student.getPassword().equals(password) && student.getBanned().equals("No")) {

            
            return ResponseEntity.ok(new LoginResponse(student.getId(),"Student", student.getName(), student.getSurname(), student.getEmail(), student.getPhoto()));
            
            
        }

        Instructor instructor = instructorRepository.findByEmail(email);
        if (instructor != null && instructor.getPassword().equals(password)) {
            return ResponseEntity.ok(new LoginResponse(instructor.getId(),"Instructor", instructor.getName(), instructor.getSurname(), instructor.getEmail(), instructor.getPhoto()));
        }

        DepartmentManager departmentManager = departmentManagerRepository.findByEmail(email);
        if (departmentManager != null && departmentManager.getPassword().equals(password)) {
            return ResponseEntity.ok(new LoginResponse(departmentManager.getId(),"DepartmentManager", departmentManager.getName(), departmentManager.getSurname(), departmentManager.getEmail(), departmentManager.getPhoto()));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
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

    public class LoginResponse {
        private long id;
        private String role;
        private String name;
        private String surname;
        private String email;
        private String photo;
    
        public LoginResponse(Long id, String role, String name, String surname, String email, String photo) {
            this.id=id;
            this.role = role;
            this.name = name;
            this.surname = surname;
            this.email = email;
            this.photo = photo;
        }
        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }
        public String getRole() {
            return role;
        }
    
        public void setRole(String role) {
            this.role = role;
        }
    
        public String getName() {
            return name;
        }
    
        public void setName(String name) {
            this.name = name;
        }
    
        public String getSurname() {
            return surname;
        }
    
        public void setSurname(String surname) {
            this.surname = surname;
        }
    
        public String getEmail() {
            return email;
        }
    
        public void setEmail(String email) {
            this.email = email;
        }
    
        public String getPhoto() {
            return photo;
        }
    
        public void setPhoto(String photo) {
            this.photo = photo;
        }
    }
    
    
}
