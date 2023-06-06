package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Technician;
import com.example.demo.repository.TechnicianRepository;

@Service
public class TechnicianService {
    @Autowired
    private TechnicianRepository technicianRepository;

    public List<Technician> getAllTechnicians(){
        return technicianRepository.findAll();
    }
    public Technician getTechnicianById(Integer id){
        Optional<Technician> optional = technicianRepository.findById(id);
        if(optional.isPresent()) return optional.get();
        else return null;
    }

}
