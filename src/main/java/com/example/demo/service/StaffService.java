package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Accountant;
import com.example.demo.model.Manager;
import com.example.demo.model.Staff;
import com.example.demo.model.Technician;
import com.example.demo.repository.AccountantRepository;
import com.example.demo.repository.ManagerRepository;
import com.example.demo.repository.StaffRepository;
import com.example.demo.repository.TechnicianRepository;

@Service
public class StaffService {
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private AccountantRepository accountantRepository;
    @Autowired
    private TechnicianRepository technicianRepository;

    public Staff getStaffByUsernameAndPassword(Staff staff){
        Optional<Staff> optional = staffRepository.findByUsernameAndPassword(staff.getUsername(), staff.getPassword());
        if(optional.isPresent()) return optional.get();
        else return null;
    }

    public Manager getManagerById(Staff staff){
        Optional<Manager> optional = managerRepository.findById(staff.getId());
        if(optional.isPresent()) return optional.get();
        else return null;
    }

    public Accountant getAccountantById(Staff staff){
        Optional<Accountant> optional = accountantRepository.findById(staff.getId());
        if(optional.isPresent()) return optional.get();
        else return null;
    }

    public Technician getTechnicianById(Staff staff){
        Optional<Technician> optional = technicianRepository.findById(staff.getId());
        if(optional.isPresent()) return optional.get();
        else return null;
    }
}
