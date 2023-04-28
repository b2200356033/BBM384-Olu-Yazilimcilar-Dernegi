package com.example.bbm384oyd.repository;

import com.example.bbm384oyd.Model.Admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    @Query("SELECT a FROM Admin a WHERE a.email = ?1")
    Optional<Admin> findByEmail(String email);
}