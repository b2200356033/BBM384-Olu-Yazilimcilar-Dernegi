package com.example.bbm384oyd.repository;

import com.example.bbm384oyd.model.Survey;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long>{
    
    
}
