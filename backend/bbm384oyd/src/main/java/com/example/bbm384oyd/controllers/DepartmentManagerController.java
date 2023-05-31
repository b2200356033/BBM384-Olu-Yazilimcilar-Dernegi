package com.example.bbm384oyd.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bbm384oyd.model.DepartmentManager;
import com.example.bbm384oyd.model.FileDB;
import com.example.bbm384oyd.repository.DepartmentManagerRepository;
import com.example.bbm384oyd.service.DepartmentManagerService;

@RestController
@RequestMapping("/departmentmanager")
public class DepartmentManagerController {
    @Autowired
    private DepartmentManagerRepository departmentManagerRepository;

    @Autowired
    private DepartmentManagerService departmentManagerService;

    @GetMapping("/{id}")
    public DepartmentManager getDepartmentManager(@PathVariable("id") Long id) {
        return departmentManagerService.getDepartmentManagerById(id);
    }

    /* @GetMapping("/{email}")
    public DepartmentManager getDepartmentManagerByEmail(@PathVariable("email") String email) {
        return departmentManagerService.findByEmail(email);
    } */
    @GetMapping("/files/{id}")
    public List<FileDB> getDepartmentManagerSources(@PathVariable("id") Long id) {
        return departmentManagerService.findById(id).getDepartmentManagerFiles();
    }
    @PostMapping
    public DepartmentManager createDepartmentManager(@RequestBody DepartmentManager departmentManager) {
        return departmentManagerService.createDepartmentManager(departmentManager);
    }
    
    @PutMapping("/{id}")
    public DepartmentManager updateDepartmentManager(@PathVariable("id") Long id, @RequestBody DepartmentManager departmentManager) {
        return departmentManagerService.updateDepartmentManager(id, departmentManager);
    }

    
    @PostMapping("/addfile/{email}")
    public void addFile(@PathVariable("email") String email, @RequestBody FileDB file) {
        System.out.println("add file mail");
        departmentManagerService.addFileDepartmentManager(email, file);
        
    }
    


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
    }

    @PutMapping("/manage/email/{email}")
    public DepartmentManager manageDepartmentManagerEmail(@PathVariable("email") String oldEmail, @RequestBody String newEmail) {
        newEmail = newEmail.replace("\"", "");
        List<DepartmentManager> list_users = departmentManagerRepository.findByEmail2(oldEmail);
        List<DepartmentManager> list_users2 = departmentManagerRepository.findByEmail2(newEmail);
        DepartmentManager copy_user = new DepartmentManager();
        DepartmentManager existing_user = null;
        if (!list_users.isEmpty()) {
            existing_user = list_users.get(0);
            if (list_users2.isEmpty()) {
                existing_user.setEmail(newEmail);
                departmentManagerRepository.save(existing_user);
            }
            else {
                copy_user.setEmail(oldEmail);
                return copy_user;
            }
        }
        return existing_user;
    }

    @PutMapping("/manage/password/{email}")
    public DepartmentManager manageDepartmentManagerPassword(@PathVariable("email") String email, @RequestParam("old") String oldPw, @RequestParam("new") String newPw) {
        System.out.println("DM");
        newPw = newPw.replace("\"", "");
        oldPw = oldPw.replace("\"", "");
        List<DepartmentManager> list_users = departmentManagerRepository.findByEmail2(email);
        DepartmentManager copy_user = new DepartmentManager();
        DepartmentManager existing_user = null;
        if (!list_users.isEmpty()) {
            existing_user = list_users.get(0);
            if (existing_user.getPassword().equals(oldPw)) {
                existing_user.setPassword(newPw);
                departmentManagerRepository.save(existing_user);
            }
            else {
                copy_user.setPassword(oldPw);
                return copy_user;
            }
        }
        return existing_user;
    }

    @DeleteMapping("/{dpid}/deletefile/{fileid}")
    public List<FileDB> deleteFile(@PathVariable("dpid") Long dpid, @PathVariable("fileid") Long fileid) {
        departmentManagerService.deleteFile(dpid, fileid);
        return departmentManagerService.findById(dpid).getDepartmentManagerFiles();
    }
}
