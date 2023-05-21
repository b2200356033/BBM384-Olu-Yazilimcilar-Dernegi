package com.example.bbm384oyd.controllers;

import org.springframework.web.bind.annotation.*;

import com.example.bbm384oyd.model.Evaluation;

@RestController
@RequestMapping("/evaluation")
public class EvaluationController {
    @GetMapping("/{id}")
    public Evaluation getEvaluation(@PathVariable("id") Long id) {
        //Dummy object
        Evaluation evaluation = new Evaluation();
        return evaluation;
    }
    
    @PostMapping
    public Evaluation createEvaluation(@RequestBody Evaluation evaluation) {
        //Dummy object
        Evaluation savedEvaluation = new Evaluation();
        return savedEvaluation;
    }
    
    @PutMapping("/{id}")
    public Evaluation updateEvaluation(@PathVariable("id") Long id, @RequestBody Evaluation evaluation) {
        //Dummy object
        Evaluation updatedEvaluation = new Evaluation();
        return updatedEvaluation;
    }

}