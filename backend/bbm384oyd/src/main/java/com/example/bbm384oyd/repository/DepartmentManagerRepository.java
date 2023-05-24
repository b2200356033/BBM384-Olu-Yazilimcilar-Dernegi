package com.example.bbm384oyd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.bbm384oyd.model.DepartmentManager;

@Repository
public interface DepartmentManagerRepository extends JpaRepository<DepartmentManager, Long> {
    DepartmentManager findByEmail(String email);

    @Query("SELECT u FROM DepartmentManager u WHERE u.name = :name AND u.surname = :surname")
    List<DepartmentManager> findByNameAndSurname(@Param("name") String name, @Param("surname") String surname);
}
