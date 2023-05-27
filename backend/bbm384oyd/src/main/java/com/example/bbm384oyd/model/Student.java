package com.example.bbm384oyd.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private String email;
    private String password;
    private String photo;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "students")
    @JsonManagedReference
    private List<Course> courses = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "student")
    private List<Evaluation> evaluations = new ArrayList<>();

    public Student() {
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
    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public List<Evaluation> getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(List<Evaluation> evaluations) {
        this.evaluations = evaluations;
    }
    public void addCourse(Course course) {
        if(!this.getCourses().contains(course)){
            courses.add(course);
            course.getStudents().add(this);
        }
        
    }
    public void dropCourse(Course course) {
        courses.remove(course);
        System.out.println("Deleting course:" +course);
        course.getStudents().remove(this);
    }

    @Override
    public String toString() {
        return "Student {id=" + id + ", name='" + name + '\'' + ", surname='" + surname + '\'' + ", email='" + email + '\'' + ", password='" + password + '\'' + ", photo='" + photo + "'}";
    }
}
