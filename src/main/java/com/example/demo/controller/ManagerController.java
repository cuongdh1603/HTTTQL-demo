package com.example.demo.controller;

import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.PlanDTO;
import com.example.demo.model.Contract;
import com.example.demo.model.Manager;
import com.example.demo.model.Technician;
import com.example.demo.service.ContractService;
import com.example.demo.service.PlanDTOService;
import com.example.demo.service.TechnicianService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
@RequestMapping("/manager")
public class ManagerController {
    @Autowired
    private ContractService contractService;
    @Autowired
    private TechnicianService technicianService;
    @Autowired
    private PlanDTOService planDTOService;
    @GetMapping
    public String managerHome(Model model, HttpSession session){
        Manager manager = (Manager)session.getAttribute("manager");
        model.addAttribute("username", manager.getUsername());

        return "manager/home";
    }
    @GetMapping("/contracts")
    public String contractView(Model model, HttpSession session){
        Manager manager = (Manager)session.getAttribute("manager");
        model.addAttribute("username", manager.getUsername());

        List<Technician> technicians = technicianService.getAllTechnicians();
        List<Contract> contracts = contractService.getAllContracts();
        model.addAttribute("technicians", technicians);
        model.addAttribute("contracts", contracts);
        return "manager/contracts";
    }
    @PostMapping("/assign/{id}")
    public String assignTechnicians(Model model, 
        @PathVariable("id") Integer id, 
        @RequestParam("technicianId") Integer technicianId,
        RedirectAttributes ra,
        HttpSession session){
        Manager manager = (Manager)session.getAttribute("manager");
        model.addAttribute("username", manager.getUsername());

        contractService.setHandledContract(id, manager.getId(), technicianId);
        System.out.println("Ma hop dong "+id+" Ma NVKT: " +technicianId);
        ra.addFlashAttribute("successAssign","Hoàn thành thao tác!" );
        return "redirect:/manager/contracts";
    }

    @GetMapping("/chart")
    public String showChart(Model model, HttpSession session) throws Exception {
        Manager manager = (Manager)session.getAttribute("manager");
        model.addAttribute("username", manager.getUsername());

        LocalDate today = LocalDate.now();
        int month = today.getMonthValue();
        int year = today.getYear();
        // int month = 4;
        // int year = 2023;
        model.addAttribute("month", month);
        model.addAttribute("year", year);

        session.setAttribute("startDate", 1);
        session.setAttribute("searchMonth", month);
        session.setAttribute("searchYear", year);
        // Get plan report
        List<PlanDTO> dtos = planDTOService.getContractsByMonthAndYear(month, year);
        int planSum = 0;
        for (PlanDTO planDTO : dtos) {
            System.out
                    .println(planDTO.getPlanName() + ' ' + planDTO.getAmount() + ' ' + planDTO.getLatestRegisterTime());
            planSum += planDTO.getAmount();
        }
        model.addAttribute("planSum", planSum);

        Map<String, Integer> planReport = new LinkedHashMap<>();
        for (PlanDTO dto : dtos) {
            planReport.put(dto.getPlanName(), dto.getAmount());
        }
        String jsonData_1 = getJsonData1(planReport);
        log.info(jsonData_1);
        model.addAttribute("jsonData_1", jsonData_1);
        session.setAttribute("planData", jsonData_1);
        // Get contract report
        Map<String, Float> contractReport = planDTOService.getContractStatusRatio(month, year);
        model.addAttribute("contractSum", planDTOService.getContractNumberByMonthAndYear(month, year));
        String jsonData_2 = getJsonData2(contractReport);
        model.addAttribute("jsonData_2", jsonData_2);
        session.setAttribute("contractData", jsonData_2);
        log.info(jsonData_2);

        // Get revenue report
        List<String> dateString = new ArrayList<String>();
        List<Long> unhandledRevenue = new ArrayList<>();
        List<Long> processingRevenue = new ArrayList<>();
        List<Long> gainedRevenue = new ArrayList<>();
        List<Contract> contracts = planDTOService.getContractsByTime(month, year);
        List<LocalDate> dates = PlanDTOService.getDaysInMonthAndYearBeforeOrEqualToday(month, year);
        long unhandledRevenueSum = 0L, processingRevenueSum = 0L, gainedRevenueSum = 0L;
        for (LocalDate date : dates) {
            dateString.add(PlanDTOService.convertFormatDate(date));
            // int count = 0;
            long unhandle = 0L, processing = 0L, gained = 0L;
            for (Contract ct : contracts) {
                if(PlanDTOService.isTimestampOnDate(ct.getRegisterTime(), date)){
                    // count++;
                    if(ct.getStatus()==0){
                        unhandle += ct.getPlan().getPrice();
                    }
                    else if(ct.getStatus()==1||ct.getStatus()==2||ct.getStatus()==3){
                        processing += ct.getPlan().getPrice();

                    }
                }
                if(ct.getStatus()==4){
                    if(PlanDTOService.isTimestampOnDate(ct.getPayTime(), date)){
                        gained += ct.getPlan().getPrice();

                    }
                }
            }
            unhandledRevenue.add(unhandle);
            unhandledRevenueSum += unhandle;
            processingRevenue.add(processing);
            processingRevenueSum += processing;
            gainedRevenue.add(gained);
            gainedRevenueSum += gained;
        }
        model.addAttribute("revenueSum", planDTOService.getRevenueSumByMonthAndYear(month, year));
        String dateSeries = getJsonDataDate(dateString);
        model.addAttribute("dateSeries", dateSeries);
        log.info("Cac ngay: "+ dateSeries);
        String unhandledSeries = getJsonDataLong(unhandledRevenue);
        model.addAttribute("unhandledSeries", unhandledSeries);
        log.info("Chua xu ly: "+ unhandledSeries);
        String processingSeries = getJsonDataLong(processingRevenue);
        model.addAttribute("processingSeries", processingSeries);
        log.info("Dang xu ly: "+ processingSeries);
        String gainedSeries = getJsonDataLong(gainedRevenue);
        model.addAttribute("gainedSeries", gainedSeries);
        log.info("Da thu: "+ gainedSeries);
        session.setAttribute("unhandledRevenue", unhandledRevenueSum);
        session.setAttribute("processingRevenueSum", processingRevenueSum);
        session.setAttribute("gainedRevenueSum", gainedRevenueSum);

        return "manager/statistic";
    }

    public static String getJsonData1(Map<String, Integer> data) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(data);
    }
    public static String getJsonData2(Map<String, Float> data) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(data);
    }
    public static String getJsonDataDate(List<String> dateStr) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(dateStr);
    }
    public static String getJsonDataLong(List<Long> amounts) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(amounts);
    }

}
