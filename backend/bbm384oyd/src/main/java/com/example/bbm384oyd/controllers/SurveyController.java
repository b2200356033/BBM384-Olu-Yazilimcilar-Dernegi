package com.example.bbm384oyd.controllers;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bbm384oyd.model.Course;
import com.example.bbm384oyd.model.Evaluation;
import com.example.bbm384oyd.model.Instructor;
import com.example.bbm384oyd.model.Student;
import com.example.bbm384oyd.model.Survey;
import com.example.bbm384oyd.model.SurveyFinder;
import com.example.bbm384oyd.repository.CourseRepository;
import com.example.bbm384oyd.repository.EvaluationRepository;
import com.example.bbm384oyd.repository.InstructorRepository;
import com.example.bbm384oyd.repository.StudentRepository;
import com.example.bbm384oyd.repository.SurveyRepository;

@RestController
@RequestMapping("/surveys")
public class SurveyController {
    @Autowired
    private SurveyRepository surveyRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private EvaluationRepository evaluationRepository;

    @GetMapping
    public ResponseEntity<List<Survey>> getAllSurveys() {
        List<Survey> surveys = surveyRepository.findAll();
        return ResponseEntity.ok(surveys);
    }
    @GetMapping("/{courseId}/survey")
    public List<String> getCourseSurvey(@PathVariable("courseId") Long courseId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if(optionalCourse.isPresent()){
            Course course = optionalCourse.get();
            Survey survey = course.getSurvey();
            System.out.println("Survey found, returning survey for evaluation"+survey);
            return survey.getQuestionList();
        }
        return Collections.emptyList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Survey> getSurveyById(@PathVariable Long id) {
        return surveyRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    
    
    @PostMapping
    public Survey createSurvey(@RequestBody SurveyFinder surveyFinder) {
        //find course and instructor first
        System.out.println("Course with the id: "+surveyFinder.getCourseId()+" is being searched...");
        Optional<Course> optionalCourse = courseRepository.findById(surveyFinder.getCourseId());
        
        Survey survey = new Survey();
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            System.out.println("The course that the survey belongs to is"+course);
            survey.setQuestionList(surveyFinder.getQuestions());
            System.out.println(surveyFinder.getQuestions());
            survey.setCourse(course);
            
        }
        

        return surveyRepository.save(survey);
    }

    @PostMapping("/{studentId}/{courseId}/evaluation")
    public void addEvalToSurvey(@PathVariable("studentId") Long studentId,@PathVariable("courseId") Long courseId, @RequestBody List<Float> answers){
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        if(optionalCourse.isPresent() && optionalStudent.isPresent()){
            Course course = optionalCourse.get();
            Student student = optionalStudent.get();
            //create evaluation object from integer list answers
            Evaluation evaluation = new Evaluation();
            evaluation.setAnswers(answers);
            evaluation.setStudent(student);

            course.getSurvey().getEvaluations().add(evaluation);

            evaluationRepository.save(evaluation);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Survey> updateSurvey(@PathVariable Long id, @RequestBody Survey survey) {
        if (!surveyRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        //here update all survey attributes
        Survey updatedSurvey = surveyRepository.save(survey);
        return ResponseEntity.ok(updatedSurvey);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSurvey(@PathVariable Long id) {
        if (!surveyRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        surveyRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
