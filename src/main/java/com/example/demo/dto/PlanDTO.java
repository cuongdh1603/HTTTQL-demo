package com.example.demo.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlanDTO {
    private String planName;
    private Integer amount;
    private Timestamp latestRegisterTime;
}
