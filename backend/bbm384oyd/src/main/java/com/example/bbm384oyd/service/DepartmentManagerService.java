package com.example.bbm384oyd.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bbm384oyd.model.DepartmentManager;
import com.example.bbm384oyd.model.FileDB;
import com.example.bbm384oyd.repository.DepartmentManagerRepository;

@Service
public class DepartmentManagerService {
    @Autowired
    private DepartmentManagerRepository departmentManagerRepository;

    @Transactional
    public DepartmentManager getDepartmentManagerById(Long departmentManagerId) {
        return departmentManagerRepository.findById(departmentManagerId)
                .orElseThrow(() -> new IllegalArgumentException("Department Manager not found"));
    }

    @Transactional
    public List<DepartmentManager> getAllDepartmentManagers() {
        return departmentManagerRepository.findAll();
    }

    @Transactional
    public DepartmentManager createDepartmentManager(DepartmentManager departmentManager) {
        return departmentManagerRepository.save(departmentManager);
    }

    @Transactional
    public DepartmentManager updateDepartmentManager(Long departmentManagerId, DepartmentManager departmentManagerDetails) {
        DepartmentManager departmentManager = departmentManagerRepository.findById(departmentManagerId)
                .orElseThrow(() -> new IllegalArgumentException("Department Manager not found"));

        departmentManager.setName(departmentManagerDetails.getName());
        departmentManager.setSurname(departmentManagerDetails.getSurname());
        departmentManager.setEmail(departmentManagerDetails.getEmail());
        departmentManager.setPassword(departmentManagerDetails.getPassword());
        departmentManager.setPhoto(departmentManagerDetails.getPhoto());

        return departmentManagerRepository.save(departmentManager);
    }
    
    @Transactional
    public DepartmentManager findByEmail(String email) {
        return departmentManagerRepository.findByEmail(email);
    }

    @Transactional
    public void deleteDepartmentManager(Long departmentManagerId) {
        DepartmentManager departmentManager = departmentManagerRepository.findById(departmentManagerId)
                .orElseThrow(() -> new IllegalArgumentException("Department Manager not found"));

        departmentManagerRepository.delete(departmentManager);
    }

    @Transactional
    public void deleteAllDepartmentManagers() {
        departmentManagerRepository.deleteAll();
    }

    @Transactional
    public boolean isDepartmentManagerExist(Long departmentManagerId) {
        return departmentManagerRepository.existsById(departmentManagerId);
    }

    @Transactional
    public boolean isDepartmentManagerExistByEmail(String email) {
        return departmentManagerRepository.findByEmail(email) != null;
    }

    @Transactional
    public List<FileDB> getDepartmentManagerSources(Long departmentManagerId) {
        DepartmentManager departmentManager = departmentManagerRepository.findById(departmentManagerId)
                .orElseThrow(() -> new IllegalArgumentException("Department Manager not found"));

        return departmentManager.getDepartmentManagerSources();
    }

    @Transactional
    public DepartmentManager addDepartmentManagerSource(Long departmentManagerId, FileDB departmentManagerSource) {
        DepartmentManager departmentManager = departmentManagerRepository.findById(departmentManagerId)
                .orElseThrow(() -> new IllegalArgumentException("Department Manager not found"));

        departmentManager.getDepartmentManagerSources().add(departmentManagerSource);

        return departmentManagerRepository.save(departmentManager);
    }

    @Transactional
    public DepartmentManager addFileDepartmentManager(String email, FileDB file) {
        DepartmentManager departmentManager = departmentManagerRepository.findByEmail(email);

        departmentManager.getDepartmentManagerSources().add(file);

        return departmentManagerRepository.save(departmentManager);
    }
    
}
