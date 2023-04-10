package com.example.demo.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_plans")
public class Plan {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "speed")
    private Integer speed;

    @Column(name = "period")
    private Integer period;

    @Column(name = "price")
    private Float price;

    @Column(name = "note")
    private String note;

    @OneToMany(mappedBy = "plan")
    private List<PlanDevice> planDevices;

    @OneToMany(mappedBy = "plan")
    private List<Contract> contracts;
}
/*
Table: tbl_plans
Columns:
id int AI PK 
name varchar(45) 
speed int 
period int 
price float 
note varchar(225)
 */