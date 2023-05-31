package com.example.bbm384oyd.controllers;


import java.util.List;
import java.util.Optional;

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
import com.example.bbm384oyd.model.Admin;
import com.example.bbm384oyd.repository.AdminRepository;

@RestController
@RequestMapping("/admin") 
public class AdminController {
    @Autowired
    private AdminRepository adminRepository;

    @GetMapping("/{id}")
    public Optional<Admin> getAdmin(@PathVariable("id") Long id) {
        return adminRepository.findById(id);
    }

    @PostMapping
    public ResponseEntity<Admin> createAdmin(@RequestBody SignupRequest signupRequest) {
        if (!adminRepository.findByEmail2(signupRequest.getEmail()).isEmpty()) {
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

    @PutMapping("/{id}")
    public Admin updateAdmin(@PathVariable("id") Long id, @RequestBody Admin adminDetails) {
        Admin admin = adminRepository.findById(id).orElseThrow();
        admin.setName(adminDetails.getName());
        admin.setSurname(adminDetails.getSurname());
        admin.setEmail(adminDetails.getEmail());
        admin.setPassword(adminDetails.getPassword());
        admin.setPhoto(adminDetails.getPhoto());
        return adminRepository.save(admin);
    }

    @DeleteMapping("/email/{email}")
    public Admin deleteAdmin(@PathVariable("email") String email) {
    List<Admin> list = adminRepository.findByEmail2(email);
    Admin user = null;
    if (list.size() != 0) {
        user = list.get(0);
        adminRepository.delete(user);
        }
    return user;
    }

    @DeleteMapping("/fullname/{name}/{surname}")
    public Admin deleteAdmin(@PathVariable("name") String name, @PathVariable("surname") String surname) {
    List<Admin> list = adminRepository.findByNameAndSurname(name, surname);
    Admin user = null;
    if (list.size() != 0) {
        user = list.get(0);
        adminRepository.delete(user);
    }
    return user;
    }


    @PutMapping("/manage/email/{email}")
    public Admin manageAdminEmail(@PathVariable("email") String oldEmail, @RequestBody String newEmail) {
        newEmail = newEmail.replace("\"", "");
        List<Admin> list_users = adminRepository.findByEmail2(oldEmail);
        List<Admin> list_users2 = adminRepository.findByEmail2(newEmail);
        Admin copy_user = new Admin();
        Admin existing_user = null;
        if (!list_users.isEmpty()) {
            existing_user = list_users.get(0);
            if (list_users2.isEmpty()) {
                existing_user.setEmail(newEmail);
                adminRepository.save(existing_user);
            }
            else {
                copy_user.setEmail(oldEmail);
                return copy_user;
            }
        }
        return existing_user;
    }

    @PutMapping("/manage/password/{email}")
    public Admin manageAdminPassword(@PathVariable("email") String email, @RequestParam("old") String oldPw, @RequestParam("new") String newPw) {
        newPw = newPw.replace("\"", "");
        oldPw = oldPw.replace("\"", "");
        List<Admin> list_users = adminRepository.findByEmail2(email);
        Admin copy_user = new Admin();
        Admin existing_user = null;
        if (!list_users.isEmpty()) {
            existing_user = list_users.get(0);
            if (existing_user.getPassword().equals(oldPw)) {
                existing_user.setPassword(newPw);
                adminRepository.save(existing_user);
            }
            else {
                copy_user.setPassword(oldPw);
                return copy_user;
            }
        }
        return existing_user;
    }
}
