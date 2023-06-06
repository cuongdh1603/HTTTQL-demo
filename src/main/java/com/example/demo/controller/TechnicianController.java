package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.Contract;
import com.example.demo.model.Technician;
import com.example.demo.service.ContractService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
@RequestMapping("/technician")
public class TechnicianController {
    @Autowired
    private ContractService contractService;
    @GetMapping
    public String technicianHome(Model model, HttpSession session){
        Technician technician = (Technician)session.getAttribute("technician");
        model.addAttribute("username", technician.getUsername());

        return "technician/home";
    }
    @GetMapping("/contracts")
    public String contractView(Model model, HttpSession session){
        Technician technician = (Technician)session.getAttribute("technician");
        model.addAttribute("username", technician.getUsername());

        List<Contract> contracts = contractService.getProcessingContract(technician.getId());
        model.addAttribute("contracts", contracts);
        return "technician/contracts";
    }
    @GetMapping("/confirm/{id}")
    public String confirmContract(Model model, 
        @PathVariable("id") Integer id,
        RedirectAttributes ra,
        HttpSession session){
        Technician technician = (Technician)session.getAttribute("technician");
        model.addAttribute("username", technician.getUsername());

        contractService.setConfirmedContract(id);
        log.info("Ma hop dong: "+id);
        ra.addFlashAttribute("successConfirm", "Bạn đã xác nhận lịch đặt này !");
        return "redirect:/technician/contracts";
    }
    @GetMapping("/install/{id}")
    public String installComplete(Model model,
        @PathVariable("id") Integer id,
        RedirectAttributes ra,
        HttpSession session){
        Technician technician = (Technician)session.getAttribute("technician");
        model.addAttribute("username", technician.getUsername());
        
        contractService.setInstalledContract(id);
        ra.addFlashAttribute("successInstall", "Bạn đã hoàn tất lắp đặt cho một hợp đồng !");
        return "redirect:/technician/contracts";
    }
}
