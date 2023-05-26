package com.example.bbm384oyd.controllers;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Admin createAdmin(@RequestBody Admin admin) {
        return adminRepository.save(admin);
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

    @DeleteMapping("/{email}")
    public Admin deleteAdmin(@PathVariable("email") String email) {
    Optional<Admin> userOptional = adminRepository.findByEmail(email);
    Admin user = null;
    if (userOptional.isPresent()) {
        user = userOptional.get();
        //System.out.println(user);
        adminRepository.delete(user);
        }
    return user;
    }

    @DeleteMapping("/{name}/{surname}")
    public Admin deleteAdmin(@PathVariable("name") String name, @PathVariable("surname") String surname) {
    Admin user = null;
    user = adminRepository.findByNameAndSurname(name, surname).get(0);
    if (user != null) {
        adminRepository.delete(user);
    }
    return user;
    }


    @DeleteMapping("/email/{email}")
    public Admin deleteAdmin(@PathVariable("email") String email) {
    List<Admin> list = adminRepository.findByEmail2(email);
    Admin user = null;
    if (list.size() != 0) {
        user = list.get(0);
        adminRepository.delete(user);
        return user;
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
}

