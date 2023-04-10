package com.example.demo.model;

import java.sql.Timestamp;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_contracts")
public class Contract {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code")
    private String code;

    @Column(name = "register_time")
    private Timestamp registerTime;

    @Column(name = "pay_time")
    private Timestamp payTime;

    @Column(name = "appoint_time")
    private Timestamp appointTime;

    @Column(name = "address")
    private String address;

    @Column(name = "note")
    private String note;

    @Column(name = "status")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;

    @ManyToOne
    @JoinColumn(name = "accountant_id")
    private Accountant accountant;

    @ManyToOne
    @JoinColumn(name = "technician_id")
    private Technician technician;

}
/*
Table: tbl_contracts
Columns:
id int PK 
code varchar(10) 
register_time datetime 
pay_time datetime 
appoint_time varchar(20) 
address varchar(255) 
status tinyint 
note varchar(255) 
client_id int 
plan_id int 
manager_id int 
accountant_id int 
technician_id int
 */