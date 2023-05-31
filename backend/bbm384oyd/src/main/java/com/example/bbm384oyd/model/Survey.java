package com.example.bbm384oyd.model;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Entity
@Table(name ="surveys")
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    
    //each course has 1 survey, one to one
    //Each survey has multiple evaluations, but each evaluation belongs to 1 survey, one to many

    @OneToOne
    @JoinColumn(name = "course_id")
    @JsonBackReference("course-survey")
    private Course course;

    @OneToMany(mappedBy = "survey")
    @JsonManagedReference("survey-evaluation")
    private List<Evaluation> evaluations = new ArrayList<>();

    private List<String> questionList;
    public List<String> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<String> questionList) {
        this.questionList = questionList;
    }

    public Survey() {
    }


    public Long getID() {
        return ID;
    }
    public void setID(Long iD) {
        ID = iD;
    }
    public Course getCourse() {
        return course;
    }
    public void setCourse(Course course) {
        this.course = course;
    }
    public List<Evaluation> getEvaluations() {
        return evaluations;
    }
    public void setEvaluations(List<Evaluation> evaluations) {
        this.evaluations = evaluations;
    }

    @Override
    public String toString() {
        return "Survey [ID=" + ID + ", questionList=" + questionList + "]";
    }

    
    
}

