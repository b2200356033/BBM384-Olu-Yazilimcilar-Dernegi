package com.example.bbm384oyd.repository;

import com.example.bbm384oyd.model.DepartmentManager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentManagerRepository extends JpaRepository<DepartmentManager, Long> {
    DepartmentManager findByEmailAndPassword(String email, String password);
}
