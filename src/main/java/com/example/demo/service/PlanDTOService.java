package com.example.demo.service;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.PlanDTO;
import com.example.demo.dto.PlanDTOComparator;
import com.example.demo.model.Contract;
import com.example.demo.model.Plan;
import com.example.demo.repository.ContractRepository;

@Service
public class PlanDTOService {
    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private PlanService planService;
    public List<Contract> getContractsByTime(Integer month, Integer year){
        return contractRepository.findContractsByMonthAndYear(month, year);
    }
    public List<PlanDTO> getContractsByMonthAndYear(Integer month, Integer year) {
        List<Contract> contracts = contractRepository.findContractsByMonthAndYear(month, year);
        List<Plan> plans = planService.getAllPlans();
        List<PlanDTO> planDTOs = new ArrayList<>();
        Timestamp currentTime = new Timestamp(new Date().getTime());
        for (Plan p : plans) {
            PlanDTO dto = new PlanDTO();
            dto.setPlanName(p.getName());
            int count = 0;
            Contract latestRegisterContract = null;
            long minDiff = Long.MAX_VALUE;
            for (Contract ct : contracts) {
                if (ct.getPlan().getName().equals(p.getName())) {
                    count++;
                    long diff = Math.abs(ct.getRegisterTime().getTime() - currentTime.getTime());
                    if (diff < minDiff) {
                        minDiff = diff;
                        latestRegisterContract = ct;
                    }
                }
            }
            dto.setAmount(count);
            if (latestRegisterContract == null)
                dto.setLatestRegisterTime(currentTime);
            else
                dto.setLatestRegisterTime(latestRegisterContract.getRegisterTime());
            planDTOs.add(dto);
        }
        Collections.sort(planDTOs, new PlanDTOComparator());
        return planDTOs;
    }

    public Map<String, Float> getContractStatusRatio(Integer month, Integer year) {
        Map<String, Float> contractReport = new LinkedHashMap<>();
        List<Contract> contracts = contractRepository.findContractsByMonthAndYear(month, year);
        Integer contractSum = contracts.size();
        int count0 = 0, count1 = 0, count2 = 0, count3 = 0, count4 = 0;
        for (Contract ct : contracts) {
            switch (ct.getStatus()) {
                case 0:
                    count0++;
                    break;
                case 1:
                    count1++;
                    break;
                case 2:
                    count2++;
                    break;
                case 3:
                    count3++;
                    break;
                case 4:
                    count4++;
                    break;
            }
        }
        contractReport.put("Chưa xử lý", (count0*100.f)/(contractSum*1.f));
        contractReport.put("Đã phân công", (count1*100.f)/(contractSum*1.f));
        contractReport.put("Đã xác nhận", (count2*100.f)/(contractSum*1.f));
        contractReport.put("Đã lắp đặt", (count3*100.f)/(contractSum*1.f));
        contractReport.put("Đã thanh toán", (count4*100.f)/(contractSum*1.f));
        return contractReport;
    }
    public static List<LocalDate> getDaysInMonthAndYearBeforeOrEqualToday(int month, int year) {
        LocalDate today = LocalDate.now();
        LocalDate endOfMonth = LocalDate.of(year, month, 1).withDayOfMonth(1).plusMonths(1).minusDays(1);
        if (endOfMonth.isAfter(today)) {
            endOfMonth = today;
        }
        List<LocalDate> daysInMonth = new ArrayList<>();
        LocalDate currentDay = LocalDate.of(year, month, 1);
        while (!currentDay.isAfter(endOfMonth)) {
            daysInMonth.add(currentDay);
            currentDay = currentDay.plusDays(1);
        }
        return daysInMonth;
    }
    public int getContractNumberByMonthAndYear(int month, int year){
        List<Contract> contracts = contractRepository.findContractsByMonthAndYear(month, year);
        return contracts.size();
    }
    public long getRevenueSumByMonthAndYear(int month, int year){
        long revenueSum = 0;
        List<Contract> contracts = contractRepository.findContractsByMonthAndYear(month, year);
        for (Contract ct : contracts) {
            if(ct.getStatus() == 4){
                revenueSum += ct.getPlan().getPrice();
            }
        }
        return revenueSum;
    }
    // public static List<LocalDate> getDatesInMonth(int month, int year){
    //     LocalDate date = LocalDate.of(year, month, 1);
    //     List<LocalDate> dates = new ArrayList<>();
    //     while (date.getMonthValue() == month) {
    //         dates.add(date);
    //         date = date.plusDays(1);
    //     }
    //     return dates;
    // }
    public static boolean isTimestampOnDate(Timestamp timestamp, LocalDate date) {
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        LocalDate localDate = localDateTime.toLocalDate();
        return localDate.isEqual(date);
    }
    public static String convertFormatDate(LocalDate localDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");
        return localDate.format(formatter);
    }
}
