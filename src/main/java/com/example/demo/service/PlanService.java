package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Plan;
import com.example.demo.repository.PlanRepository;

@Service
public class PlanService {
    @Autowired
    private PlanRepository planRepository;

    public List<Plan> getAllPlans(){
        return planRepository.findAll();
    }

    public Plan getPlanById(Integer id){
        Optional<Plan> op = planRepository.findById(id);
        if(op.isPresent()) return op.get();
        else return null;
    }

}
