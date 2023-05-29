package com.example.bbm384oyd.controllers;


import java.util.List;

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

import com.example.bbm384oyd.model.Survey;
import com.example.bbm384oyd.repository.SurveyRepository;

@RestController
@RequestMapping("/surveys")
public class SurveyController {
    @Autowired
    private SurveyRepository surveyRepository;

    @GetMapping
    public ResponseEntity<List<Survey>> getAllSurveys() {
        List<Survey> surveys = surveyRepository.findAll();
        return ResponseEntity.ok(surveys);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Survey> getSurveyById(@PathVariable Long id) {
        return surveyRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /*@PostMapping
    public Survey createSurvey(@RequestBody Survey survey){
        return surveyRepository.save(survey);
    }*/
    
    @PostMapping
    public ResponseEntity<Survey> createSurvey(@RequestBody Survey survey) {
        Survey createdSurvey = surveyRepository.save(survey);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSurvey);
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
