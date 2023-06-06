package com.example.demo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.Client;
import com.example.demo.model.Contract;
import com.example.demo.model.Plan;
import com.example.demo.service.ContractService;
import com.example.demo.service.PlanService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
@RequestMapping("/home")
public class ClientController {
    @Autowired
    private PlanService planService;
    @Autowired
    private ContractService contractService;
    @GetMapping
    public String clientHome(Model model, HttpSession session){
        Client client = (Client)session.getAttribute("client");
        model.addAttribute("username", client.getUsername());

        List<Plan> plans = planService.getAllPlans();
        log.info("So luong: " + plans.size());
        model.addAttribute("plans", plans);
        return "client/home";
    }
    @GetMapping("/register/{id}")
    public String planRegister(Model model, 
        HttpSession session,
        @PathVariable("id") Integer id){
        Client client = (Client)session.getAttribute("client");
        model.addAttribute("username", client.getUsername());

        Plan plan = planService.getPlanById(id);
        session.setAttribute("plan", plan);
        Contract contract = new Contract();
        contract.setClient(client);
        contract.setPlan(plan);
        // model.addAttribute("plan", plan);
        model.addAttribute("contract", contract);
        return "client/register";
    }
    @PostMapping("/saveContract")
    public String saveRegister(Model model,
        @Valid @ModelAttribute("contract") Contract contract,
        BindingResult result,
        RedirectAttributes ra,
        HttpSession session) {
        Client client = (Client)session.getAttribute("client");
        model.addAttribute("username", client.getUsername());
        Plan plan = (Plan)session.getAttribute("plan");

        contract.setClient(client);
        contract.setPlan(plan);
        if(result.hasErrors()){
            
            model.addAttribute("contract", contract);
            return "client/register";
        }
        contract = contractService.setDetailedInfor(contract);
        contractService.saveContract(contract);
        ra.addFlashAttribute("registerSuccess", "Gói mạng của quý khách đã được đăng kí thành công. Xin hãy chờ phản hồi xác nhận");
        log.info("Thong tin hop dong: "+contract.getAppointTime());
        return "redirect:/home/register/"+plan.getId();
    }
}
