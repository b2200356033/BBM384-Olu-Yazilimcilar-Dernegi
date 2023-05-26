package com.example.bbm384oyd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.bbm384oyd.model.Instructor;



@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    //find instructor by email
    Instructor findByEmail(String email);

    @Query("SELECT i FROM Instructor i WHERE i.email = ?1")
    List<Instructor> findByEmail2(String email);

    @Query("SELECT u FROM Instructor u WHERE u.name = ?1 AND u.surname = ?2")
    List<Instructor> findByNameAndSurname(String name, String surname);
}
