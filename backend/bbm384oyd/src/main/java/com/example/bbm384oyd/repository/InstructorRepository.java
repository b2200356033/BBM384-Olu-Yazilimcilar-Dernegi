package com.example.bbm384oyd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.bbm384oyd.model.Instructor;


@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    //find instructor by email
    Instructor findByEmail(String email);


    @Query("SELECT i FROM Instructor i WHERE i.email = :email")
    List<Instructor> findByEmail2(@Param("email") String email);
  
    @Query("SELECT u FROM Instructor u WHERE u.name = :name AND u.surname = :surname")
    List<Instructor> findByNameAndSurname(@Param("name") String name, @Param("surname") String surname);

}
