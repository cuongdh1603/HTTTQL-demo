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
@Table(name = "tbl_accountants")
@PrimaryKeyJoinColumn(name = "staff_id")
public class Accountant extends Staff{
    @Column(name = "knowledge")
    private String knowledge;

    @OneToMany(mappedBy = "accountant")
    private List<Contract> contracts;
}
/*
Table: tbl_accountants
Columns:
knowledge varchar(100) 
staff_id int PK
 */