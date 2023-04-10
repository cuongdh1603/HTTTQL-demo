package com.example.demo.model;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_support_tickets")
public class SupportTicket {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "create_date")
    private Timestamp createDate;

    @Column(name = "complete_date")
    private Timestamp completeDate;

    @Column(name = "status")
    private Integer status;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JoinColumn(name = "technician_id")
    private Technician technician;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
}
/*
Table: tbl_support_tickets
Columns:
id int PK            /
create_date datetime /
note varchar(255)    /
status tinyint       /
complete_date datetime /
technician_id int 
client_id int
 */