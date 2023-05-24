package com.example.bbm384oyd.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.bbm384oyd.model.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByEmail(String email);

    @Query("SELECT u FROM Admin u WHERE u.name = :name AND u.surname = :surname")
    List<Admin> findByNameAndSurname(@Param("name") String name, @Param("surname") String surname);

    List<Admin> findAll();
}



