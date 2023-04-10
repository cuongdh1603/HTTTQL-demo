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
@Table(name = "tbl_devices")
public class Device {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "type_device")
    private String type;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Float price;

    @Column(name = "status")
    private String status;

    @Column(name = "note")
    private String note;

    @OneToMany(mappedBy = "device")
    private List<PlanDevice> planDevices;
}
/*
Table: tbl_devices
Columns:
id int PK 
type_device varchar(45) 
name varchar(45) 
price float 
status varchar(100) 
note varchar(45)
 */