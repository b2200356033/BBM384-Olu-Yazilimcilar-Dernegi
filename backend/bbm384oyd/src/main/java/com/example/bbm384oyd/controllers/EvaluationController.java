package com.example.bbm384oyd.controllers;

import org.springframework.web.bind.annotation.*;

import com.example.bbm384oyd.Model.Evaluation;

@RestController
@RequestMapping("/evaluation")
public class EvaluationController {
    @GetMapping("/{id}")
    public Evaluation getEvaluation(@PathVariable("id") Long id) {
        // retrieve evaluation with given id from database

        //Dummy object
        Evaluation evaluation = new Evaluation();
        return evaluation;
    }
    
    @PostMapping
    public Evaluation createEvaluation(@RequestBody Evaluation evaluation) {
        // save evaluation to database and return saved evaluation with generated id

        //Dummy object
        Evaluation savedEvaluation = new Evaluation();
        return savedEvaluation;
    }
    
    @PutMapping("/{id}")
    public Evaluation updateEvaluation(@PathVariable("id") Long id, @RequestBody Evaluation evaluation) {
        // update evaluation with given id in
        
        //Dummy object
        Evaluation updatedEvaluation = new Evaluation();
        return updatedEvaluation;
    }

}