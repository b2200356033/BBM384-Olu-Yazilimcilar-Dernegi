package com.example.bbm384oyd.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.bbm384oyd.model.DepartmentManager;
import com.example.bbm384oyd.model.FileDB;
import com.example.bbm384oyd.service.DepartmentManagerService;

@RestController
@RequestMapping("/departmentmanager")
public class DepartmentManagerController {
    @Autowired
    private DepartmentManagerService departmentManagerService;
    @GetMapping("/")
    public List<DepartmentManager> getAllDepartmentManagers() {
        // retrieve all departmentManagers from database and return them
        return departmentManagerService.getAllDepartmentManagers();
    }

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
        return departmentManagerService.createDepartmentManager(departmentManager);
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
    
    @DeleteMapping("/{id}")
    public void deleteDepartmentManager(@PathVariable("id") Long id) {
        departmentManagerService.deleteDepartmentManager(id);
    }
}
