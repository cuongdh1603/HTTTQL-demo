package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Client;

public interface ClientRepository extends JpaRepository<Client, Integer>{
    Optional<Client> findById(Integer id);
    Optional<Client> findByUsername(String username);

    Optional<Client> findByEmail(String email);
    Optional<Client> findByPhone(String phone);
}
