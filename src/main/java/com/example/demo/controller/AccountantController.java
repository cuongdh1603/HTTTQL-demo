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

import com.example.demo.model.Accountant;
import com.example.demo.model.Contract;
import com.example.demo.service.ContractService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
@RequestMapping("/accountant")
public class AccountantController {
    @Autowired
    private ContractService contractService;
    @GetMapping
    public String accountantHome(Model model, HttpSession session){
        Accountant accountant = (Accountant)session.getAttribute("accountant");
        model.addAttribute("username", accountant.getUsername());
        return "accountant/home";
    }
    @GetMapping("/contracts")
    public String contractView(Model model, HttpSession session){
        Accountant accountant = (Accountant)session.getAttribute("accountant");
        model.addAttribute("username", accountant.getUsername());

        List<Contract> contracts = contractService.getInstalledContracts();
        model.addAttribute("contracts", contracts);
        return "accountant/contracts";
    }
    @PostMapping("/pay/{id}")
    public String contractPayment(Model model,
        @PathVariable("id") Integer id, 
        @RequestParam("payAmount") Long payAmount,
        RedirectAttributes ra, 
        HttpSession session){
        Accountant accountant = (Accountant)session.getAttribute("accountant");
        model.addAttribute("username", accountant.getUsername());

        log.info("hop dong: "+id+" So tien: "+Float.valueOf(payAmount));
        contractService.setPaidContract(id, accountant.getId(), Float.valueOf(payAmount));
        ra.addFlashAttribute("successPayment", "Hoàn tất thanh toán !");
        return "redirect:/accountant/contracts";
    }
}
