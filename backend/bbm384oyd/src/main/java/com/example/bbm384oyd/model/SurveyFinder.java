package com.example.bbm384oyd.model;

import java.util.List;

public class SurveyFinder {
    private Long id;
    private Long courseId;
    private Long instructorId;
    private List<String> questions;
    public SurveyFinder(Long id, Long courseId, Long instructorId, List<String> questions) {
        this.id = id;
        this.courseId = courseId;
        this.instructorId = instructorId;
        this.questions = questions;
    }
    
    public SurveyFinder() {
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getCourseId() {
        return courseId;
    }
    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
    public Long getInstructorId() {
        return instructorId;
    }
    public void setInstructorId(Long instructorId) {
        this.instructorId = instructorId;
    }
    public List<String> getQuestions() {
        return questions;
    }
    public void setQuestions(List<String> questions) {
        this.questions = questions;
    }

    
}
