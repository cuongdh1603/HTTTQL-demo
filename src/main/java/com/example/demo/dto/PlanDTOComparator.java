package com.example.demo.dto;

import java.util.Comparator;

public class PlanDTOComparator implements Comparator<PlanDTO>{

    @Override
    public int compare(PlanDTO dto1, PlanDTO dto2) {
        int amountCompare = dto1.getAmount().compareTo(dto2.getAmount());
        if (amountCompare != 0) {
            return -amountCompare;
        }
        return -dto1.getLatestRegisterTime().compareTo(dto2.getLatestRegisterTime());
    }

    
}
