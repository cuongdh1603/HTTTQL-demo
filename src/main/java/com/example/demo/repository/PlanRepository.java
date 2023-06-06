package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Plan;
@Repository
public interface PlanRepository extends JpaRepository<Plan, Integer>{
    Optional<Plan> findById(Integer id);
}
