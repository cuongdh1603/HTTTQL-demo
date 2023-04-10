package com.example.demo.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
@Table(name = "tbl_technicians")
@PrimaryKeyJoinColumn(name = "staff_id")
public class Technician extends Staff{
    @Column(name = "level")
    private String level;

    @OneToMany(mappedBy = "technician")
    private List<Contract> contracts;

    @OneToMany(mappedBy = "technician")
    private List<SupportTicket> supportTickets;
}
/*
Table: tbl_technicians
Columns:
level varchar(30) 
staff_id int PK
 */