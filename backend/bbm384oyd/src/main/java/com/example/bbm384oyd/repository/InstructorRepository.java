package com.example.bbm384oyd.repository;

import com.example.bbm384oyd.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

//@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    //find instructor by email
    @Query("SELECT i FROM Instructor i WHERE i.email = ?1")
    Instructor findByEmail(String email);
}
