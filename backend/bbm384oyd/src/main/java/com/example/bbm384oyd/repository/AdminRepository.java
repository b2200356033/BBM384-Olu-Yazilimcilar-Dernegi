package com.example.bbm384oyd.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.bbm384oyd.model.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByEmail(String email);
    
    @Query("SELECT i FROM Admin i WHERE i.email = ?1")
    List<Admin> findByEmail2(String email);


    List<Admin> findAll();
}



