package com.example.demo.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.excel.ContractExcelReporter;
import com.example.demo.excel.PlanExcelReporter;
import com.example.demo.excel.RevenueExcelReporter;
import com.example.demo.model.Plan;
import com.example.demo.service.PlanService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/excel")
public class ExcelController {
    @Autowired
    private PlanService planService;

    @GetMapping("/plan")
    public void exportPlanReportToExcel(HttpServletResponse response, HttpSession session) throws IOException{
        response.setContentType("application/octet-stream");
        String fileName = "Báo cáo gói mạng.xlsx";
        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString()).replaceAll("\\+", "%20");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=\"" + encodedFileName + "\"";
        response.setHeader(headerKey, headerValue);

        List<Plan> planList = planService.getAllPlans();
        PlanExcelReporter exp = new PlanExcelReporter();
        exp.export(response, session, planList);
    }
    @GetMapping("/contract")
    public void exportContractReportToExcel(HttpServletResponse response, HttpSession session) throws IOException{
        response.setContentType("application/octet-stream");
        String fileName = "Báo cáo trạng thái hợp đồng.xlsx";
        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString()).replaceAll("\\+", "%20");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=\"" + encodedFileName + "\"";
        response.setHeader(headerKey, headerValue);
        ContractExcelReporter cer = new ContractExcelReporter();
        cer.export(response, session);
    }
    @GetMapping("/revenue")
    public void exportRevenueReportToExcel(HttpServletResponse response, HttpSession session) throws IOException{
        response.setContentType("application/octet-stream");
        String fileName = "Báo cáo doanh thu.xlsx";
        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString()).replaceAll("\\+", "%20");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=\"" + encodedFileName + "\"";
        response.setHeader(headerKey, headerValue);
        RevenueExcelReporter rer = new RevenueExcelReporter();
        rer.export(response, session);
    }
}
