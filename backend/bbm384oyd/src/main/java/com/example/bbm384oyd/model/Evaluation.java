package com.example.bbm384oyd.model;
import com.fasterxml.jackson.annotation.JsonBackReference;


import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.JoinColumn;


@Entity
@Table(name = "evaluations")
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    @JsonBackReference("survey-evaluation")
    private Survey survey;
        
    @ManyToOne
    @JoinColumn(name = "student_id")
    @JsonBackReference("evaluation-student")
    private Student student;
    
    
    
    public Long getID() {
        return ID;
    }


    public void setID(Long iD) {
        ID = iD;
    }

    public Student getStudent() {
        return student;
    }


    public void setStudent(Student student) {
        this.student = student;
    }


    public Evaluation(){
        
    }
}