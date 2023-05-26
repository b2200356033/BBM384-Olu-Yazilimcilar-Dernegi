package com.example.bbm384oyd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.bbm384oyd.model.DepartmentManager;

@Repository
public interface DepartmentManagerRepository extends JpaRepository<DepartmentManager, Long> {
    
    DepartmentManager findByEmail(String email);

    @Query("SELECT i FROM DepartmentManager i WHERE i.email = ?1")
    List<DepartmentManager> findByEmail2(String email);

    @Query("SELECT u FROM DepartmentManager u WHERE u.name = ?1 AND u.surname = ?2")
    List<DepartmentManager> findByNameAndSurname(String name, String surname);
}
