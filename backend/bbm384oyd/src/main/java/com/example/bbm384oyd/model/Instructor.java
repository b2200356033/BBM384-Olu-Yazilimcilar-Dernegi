package com.example.bbm384oyd.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "instructors")
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private String email;
    private String password;
    private String photo;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "instructor")
    @JsonManagedReference
    private List<Course> courses = new ArrayList<>();

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public Instructor() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Instructor [id=" + id + ", name=" + name + ", surname=" + surname + ", email=" + email + ", password=" + password + ", photo=" + photo + "]";
    }
    public List<Evaluation> getEvaluationsForCourses() {
        List<Evaluation> evaluations = new ArrayList<>();
        for (Course course : courses) {
            if(course.getSurvey() != null)
                evaluations.addAll(course.getSurvey().getEvaluations());
        }
        return evaluations;
    }
    //get evaluations for a specific course
    public List<Evaluation> getEvaluationsForCourses(Course course) {
        List<Evaluation> evaluations = new ArrayList<>();
        for (Course courseTemp : courses) {
            if(courseTemp.equals(course)){
                if(courseTemp.getSurvey() != null)
                    evaluations.addAll(courseTemp.getSurvey().getEvaluations());
            }
            
        }
        return evaluations;
    } 
}
