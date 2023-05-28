package com.example.bbm384oyd.controllers;

<<<<<<< HEAD
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.bbm384oyd.model.DepartmentManager;
import com.example.bbm384oyd.model.FileDB;
import com.example.bbm384oyd.service.DepartmentManagerService;
=======
import java.util.List;

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
>>>>>>> main

@RestController
@RequestMapping("/departmentmanager")
public class DepartmentManagerController {
    @Autowired
<<<<<<< HEAD
    private DepartmentManagerService departmentManagerService;
    @GetMapping("/")
    public List<DepartmentManager> getAllDepartmentManagers() {
        // retrieve all departmentManagers from database and return them
        return departmentManagerService.getAllDepartmentManagers();
    }
=======
    private DepartmentManagerRepository departmentManagerRepository;
>>>>>>> main

    @GetMapping("/{id}")
    public DepartmentManager getDepartmentManager(@PathVariable("id") Long id) {
        return departmentManagerService.getDepartmentManagerById(id);
    }

    @GetMapping("/{email}")
    public DepartmentManager getDepartmentManagerByEmail(@PathVariable("email") String email) {
        return departmentManagerService.findByEmail(email);
    }
    @GetMapping("/{email}/files")
    public List<FileDB> getDepartmentManagerSources(@PathVariable("email") String email) {
        return departmentManagerService.findByEmail(email).getDepartmentManagerSources();
    }
    @PostMapping
    public DepartmentManager createDepartmentManager(@RequestBody DepartmentManager departmentManager) {
<<<<<<< HEAD
        return departmentManagerService.createDepartmentManager(departmentManager);
=======
        return departmentManagerRepository.save(departmentManager);
>>>>>>> main
    }
    
    @PutMapping("/{id}")
    public DepartmentManager updateDepartmentManager(@PathVariable("id") Long id, @RequestBody DepartmentManager departmentManager) {
        return departmentManagerService.updateDepartmentManager(id, departmentManager);
    }

    @PutMapping("/{email}/addFile")
    public String addFile(@PathVariable("email") String email, @RequestBody FileDB file) {
        
        departmentManagerService.addFileDepartmentManager(email, file);
        return "success";
    }
    
<<<<<<< HEAD
    @DeleteMapping("/{id}")
    public void deleteDepartmentManager(@PathVariable("id") Long id) {
        departmentManagerService.deleteDepartmentManager(id);
=======


    @DeleteMapping("/email/{email}")
        public DepartmentManager deleteDepartmentManager(@PathVariable("email") String email) {
        List<DepartmentManager> list = departmentManagerRepository.findByEmail2(email);
        DepartmentManager user = null;
        if (list.size() != 0) {
            user = list.get(0);
            departmentManagerRepository.delete(user);
        }
        return user;
    }


    @DeleteMapping("/fullname/{name}/{surname}")
        public DepartmentManager deleteDepartmentManager(@PathVariable("name") String name, @PathVariable("surname") String surname) {
        DepartmentManager user = null;
        List<DepartmentManager> list = departmentManagerRepository.findByNameAndSurname(name, surname);
        if (list.size() != 0) {
            user = list.get(0);
            departmentManagerRepository.delete(user);
        }
        return user;
>>>>>>> main
    }
}
