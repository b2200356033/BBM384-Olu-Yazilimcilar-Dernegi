package com.example.bbm384oyd.controllers;

import org.springframework.web.bind.annotation.*;
import com.example.bbm384oyd.model.DepartmentManager;

@RestController
@RequestMapping("/department-manager")
public class DepartmentManagerController {
    @GetMapping("/{id}")
    public DepartmentManager getDepartmentManager(@PathVariable("id") Long id) {
        // retrieve department manager with given id from database


        //Dummy object
        DepartmentManager departmentManager = new DepartmentManager();

        return departmentManager;
    }
    
    @PostMapping
    public DepartmentManager createDepartmentManager(@RequestBody DepartmentManager departmentManager) {
        // save department manager to database and return saved department manager with generated id

        //Dummy object
        DepartmentManager savedDepartmentManager = new DepartmentManager();
        return savedDepartmentManager;
    }
    
    @PutMapping("/{id}")
    public DepartmentManager updateDepartmentManager(@PathVariable("id") Long id, @RequestBody DepartmentManager departmentManager) {
        // update department manager with given id in database using department manager object passed in request body
        
        //Dummy object
        DepartmentManager updatedDepartmentManager = new DepartmentManager();

        return updatedDepartmentManager;
    }
    
    @DeleteMapping("/{id}")
    public void deleteDepartmentManager(@PathVariable("id") Long id) {
        // delete department manager with given id from database
    }
}
