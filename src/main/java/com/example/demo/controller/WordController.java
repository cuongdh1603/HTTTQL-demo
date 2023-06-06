package com.example.demo.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import com.example.demo.word.ContractWordReporter;
import com.example.demo.word.PlanWordReporter;
import com.example.demo.word.RevenueWordReporter;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/word")
public class WordController {
    @GetMapping(value = "/plan", produces = "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
    public ResponseEntity<InputStreamResource> exportPlanReportToWord(HttpSession session)
            throws InvalidFormatException, IOException {
        /*--------------------------------- */
        String imageURL = (String) session.getAttribute("imageURL");
        log.info("Da goi Duong dan: " + imageURL);
        /*--------------------------------- */
        ByteArrayInputStream bis = PlanWordReporter.generateWord(session);
        HttpHeaders headers = new HttpHeaders();
        String fileName = "Báo cáo gói mạng.docx";
        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString()).replaceAll("\\+", "%20");
        headers.add("Content-Disposition", "inline; filename=\"" + encodedFileName + "\"");
        return ResponseEntity.ok().headers(headers).body(new InputStreamResource(bis));
    }
    @GetMapping(value = "/contract", produces = "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
    public ResponseEntity<InputStreamResource> exportContractReportToWord(HttpSession session)
            throws InvalidFormatException, IOException {
        ByteArrayInputStream bis = ContractWordReporter.generateWord(session);
        HttpHeaders headers = new HttpHeaders();
        String fileName = "Báo cáo trạng thái hợp đồng.docx";
        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString()).replaceAll("\\+", "%20");
        headers.add("Content-Disposition", "inline; filename=\"" + encodedFileName + "\"");
        return ResponseEntity.ok().headers(headers).body(new InputStreamResource(bis));
    }
    @GetMapping(value = "/revenue", produces = "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
    public ResponseEntity<InputStreamResource> exportRevenueReportToWord(HttpSession session)
            throws InvalidFormatException, IOException {
        ByteArrayInputStream bis = RevenueWordReporter.generateWord(session);
        HttpHeaders headers = new HttpHeaders();
        String fileName = "Báo cáo doanh thu.docx";
        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString()).replaceAll("\\+", "%20");
        headers.add("Content-Disposition", "inline; filename=\"" + encodedFileName + "\"");
        return ResponseEntity.ok().headers(headers).body(new InputStreamResource(bis));
    }
}
