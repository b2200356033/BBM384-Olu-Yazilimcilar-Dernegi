package com.example.bbm384oyd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bbm384oyd.model.Instructor;
import com.example.bbm384oyd.repository.InstructorRepository;


@Service
public class InstructorService {
    @Autowired
    private InstructorRepository instructorRepository;

    @org.springframework.transaction.annotation.Transactional
    public Instructor updateInstructor(Long instructorId, Instructor updatedInstructor) {
        Instructor existingInstructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new IllegalArgumentException("Instructor not found"));

        existingInstructor.setName(updatedInstructor.getName());
        existingInstructor.setSurname(updatedInstructor.getSurname());
        existingInstructor.setEmail(updatedInstructor.getEmail());
        existingInstructor.setPassword(updatedInstructor.getPassword());
        existingInstructor.setPhoto(updatedInstructor.getPhoto());
    
        instructorRepository.save(existingInstructor);
        return existingInstructor;
    }

    @Transactional
    public void deleteInstructorById(Long instructorId) {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new IllegalArgumentException("Instructor not found"));

        instructorRepository.delete(instructor);
    }

    //add instructor
    @Transactional
    public Instructor addInstructor(Instructor instructor) {
        instructorRepository.save(instructor);
        return instructor;
    }
    


    @Transactional
    public List<Instructor> getAllInstructors() {
        return instructorRepository.findAll();
    }

    //get instructor by id
    @Transactional
    public Instructor getInstructorById(Long instructorId) {
        return instructorRepository.findById(instructorId)
                .orElseThrow(() -> new IllegalArgumentException("Instructor not found"));
    }

    



    
}
