package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Integer>{
    Optional<Manager> findById(Integer id);
}
