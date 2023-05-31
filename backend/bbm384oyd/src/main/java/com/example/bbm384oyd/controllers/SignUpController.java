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
    public ResponseEntity<Admin> signupAdmin(@RequestBody SignupRequest signupRequest) {
        if (adminRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Email is already registered
        }
        

        Admin admin = new Admin(); 
        admin.setName(signupRequest.getName());
        admin.setSurname(signupRequest.getSurname());
        admin.setEmail(signupRequest.getEmail());
        admin.setPassword(signupRequest.getPassword());
        admin.setPhoto(signupRequest.getPhoto());
        adminRepository.save(admin);

        return ResponseEntity.ok(admin);
    }

    @PostMapping("/signupStudent")
    public ResponseEntity<Student> signupStudent(@RequestBody SignupRequest signupRequest) {
        if (studentRepository.findByEmail(signupRequest.getEmail()) != null ) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Email is already registered
        }
    
        Student student = new Student();
        student.setName(signupRequest.getName());
        student.setSurname(signupRequest.getSurname());
        student.setEmail(signupRequest.getEmail());
        student.setPassword(signupRequest.getPassword());  // Ensure you hash the password before storing it
        student.setPhoto(signupRequest.getPhoto());
        student.setBanned("No"); 
    
        studentRepository.save(student);
        return ResponseEntity.ok(student);
    }

    @PostMapping("/signupInstructor")
    public ResponseEntity<Instructor> signupInstructor(@RequestBody SignupRequest signupRequest) {
        if (instructorRepository.findByEmail(signupRequest.getEmail()) != null) {
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

    @PostMapping("/signupDepartmentManager")
    public ResponseEntity<DepartmentManager> signupDepartmentManager(@RequestBody SignupRequest signupRequest) {
        if (departmentManagerRepository.findByEmail(signupRequest.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Email is already registered
        }
    
        DepartmentManager departmentManager = new DepartmentManager();
        departmentManager.setName(signupRequest.getName());
        departmentManager.setSurname(signupRequest.getSurname());
        departmentManager.setEmail(signupRequest.getEmail());
        departmentManager.setPassword(signupRequest.getPassword()); // Remember to hash the password before storing it
        departmentManager.setPhoto(signupRequest.getPhoto());
    
        departmentManagerRepository.save(departmentManager);
        return ResponseEntity.ok(departmentManager);
    }
    


    public static class SignupRequest {

        private String password;
        private String name;
        private String surname;
        private String email;
        private String photo;

        public SignupRequest() {}

        public SignupRequest(String role, String name, String surname, String email, String photo) {

            this.name = name;
            this.surname = surname;
            this.email = email;
            this.photo = photo;
        }


        public String getPassword() {
            return password;
        }
    
        public void setPassword(String password) {
            this.password = password;
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
