package com.example.bbm384oyd.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bbm384oyd.model.DepartmentManager;
import com.example.bbm384oyd.repository.DepartmentManagerRepository;

@RestController
@RequestMapping("/departmentmanager")
public class DepartmentManagerController {
    @Autowired
    private DepartmentManagerRepository departmentManagerRepository;

    @GetMapping("/{id}")
    public DepartmentManager getDepartmentManager(@PathVariable("id") Long id) {
        DepartmentManager departmentManager = new DepartmentManager();
        return departmentManager;
    }
    
    @PostMapping
    public DepartmentManager createDepartmentManager(@RequestBody DepartmentManager departmentManager) {
        //Dummy object
        DepartmentManager savedDepartmentManager = new DepartmentManager();
        return savedDepartmentManager;
    }
    
    @PutMapping("/{id}")
    public DepartmentManager updateDepartmentManager(@PathVariable("id") Long id, @RequestBody DepartmentManager departmentManager) {
        //Dummy object
        DepartmentManager updatedDepartmentManager = new DepartmentManager();
        return updatedDepartmentManager;
    }
    
    @DeleteMapping("/{email}")
        public DepartmentManager deleteDepartmentManager(@PathVariable("email") String email) {
        DepartmentManager user = null;
        user = departmentManagerRepository.findByEmail(email);
        if (user != null) {
            departmentManagerRepository.delete(user);
        }
        return user;
    }

    @DeleteMapping("/{name}/{surname}")
        public DepartmentManager deleteDepartmentManager(@PathVariable("name") String name, @PathVariable("surname") String surname) {
        DepartmentManager user = null;
        user = departmentManagerRepository.findByNameAndSurname(name, surname).get(0);
        if (user != null) {
            departmentManagerRepository.delete(user);
        }
        return user;
    }
}
