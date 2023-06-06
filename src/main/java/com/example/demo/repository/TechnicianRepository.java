package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Technician;

public interface TechnicianRepository extends JpaRepository<Technician, Integer>{
    Optional<Technician> findById(Integer id);
}
