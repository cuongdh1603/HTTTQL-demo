package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Accountant;

public interface AccountantRepository extends JpaRepository<Accountant, Integer>{
    Optional<Accountant> findById(Integer id);
}
