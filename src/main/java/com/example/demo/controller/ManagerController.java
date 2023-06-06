package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.Contract;
import com.example.demo.model.Manager;
import com.example.demo.model.Technician;
import com.example.demo.service.ContractService;
import com.example.demo.service.TechnicianService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/manager")
public class ManagerController {
    @Autowired
    private ContractService contractService;
    @Autowired
    private TechnicianService technicianService;
    // @GetMapping("/unhandled")
    // public void getListContract(){
    //     List<Contract> contracts = contractService.getUnhandledContracts();
    //     for (Contract contract : contracts) {
    //         System.out.println("--> " + contract.getCode());

    //     }
    
    // }
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



}
