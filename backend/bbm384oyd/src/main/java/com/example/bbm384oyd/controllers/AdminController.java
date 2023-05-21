package com.example.bbm384oyd.controllers;

import org.springframework.web.bind.annotation.*;
import com.example.bbm384oyd.model.Admin;
import com.example.bbm384oyd.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

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

    @DeleteMapping("/{id}")
    public void deleteAdmin(@PathVariable("id") Long id) {
        adminRepository.deleteById(id);
    }
}
