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
@Table(name = "tbl_managers")
@PrimaryKeyJoinColumn(name = "staff_id")
public class Manager extends Staff{
    @Column(name = "department")
    private String department;

    @OneToMany(mappedBy = "manager")
    private List<Contract> contracts;
}
/*
Table: tbl_managers
Columns:
department varchar(20) 
staff_id int PK
 */