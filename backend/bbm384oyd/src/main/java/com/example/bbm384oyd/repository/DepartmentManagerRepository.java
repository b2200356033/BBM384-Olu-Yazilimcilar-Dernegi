package com.example.bbm384oyd.repository;

import com.example.bbm384oyd.model.DepartmentManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentManagerRepository extends JpaRepository<DepartmentManager, Long> {
    DepartmentManager findByEmail(String email);
}
