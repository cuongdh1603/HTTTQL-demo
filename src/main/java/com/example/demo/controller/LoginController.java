package com.example.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.PlanDTO;
import com.example.demo.model.Accountant;
import com.example.demo.model.Client;
import com.example.demo.model.Contract;
import com.example.demo.model.Manager;
import com.example.demo.model.Staff;
import com.example.demo.model.Technician;
import com.example.demo.service.ClientService;
import com.example.demo.service.PlanDTOService;
import com.example.demo.service.StaffService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class LoginController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private StaffService staffService;

    /*--------------- */
    @Autowired
    private PlanDTOService planDTOService;
    // @Autowired
    // private ContractService contractService;

    /*--------------- */
    @GetMapping("/login")
    public String login(Model model) {
        Client client = new Client();
        model.addAttribute("client", client);
        return "login";
    }

    @PostMapping("/post_login")
    public String postLogin(Model model,
            @ModelAttribute("customer") Client client,
            HttpSession session) {
        Client loggedClient = clientService.getClientByUsername(client);
        if (loggedClient != null) {
            log.info("Infor logged client: " + loggedClient.getName());
            session.setAttribute("client", loggedClient);
            return "redirect:/home";
        } else {
            Staff staff = new Staff();
            staff.setUsername(client.getUsername());
            staff.setPassword(client.getPassword());
            Staff loggedStaff = staffService.getStaffByUsernameAndPassword(staff);

            if (loggedStaff != null) {
                log.info("Co ket qua");
                Manager loggedManager = staffService.getManagerById(loggedStaff);
                Accountant loggedAccountant = staffService.getAccountantById(loggedStaff);
                Technician loggedTechnician = staffService.getTechnicianById(loggedStaff);
                if (loggedManager != null) {
                    log.info("Thong tin quan li: " + loggedManager.getName());
                    session.setAttribute("manager", loggedManager);
                    return "redirect:/manager";
                }
                if (loggedAccountant != null) {
                    log.info("Thong tin NV tai chinh: " + loggedAccountant.getName());
                    session.setAttribute("accountant", loggedAccountant);
                    return "redirect:/accountant";
                }
                if (loggedTechnician != null) {
                    log.info("Thong tin NV ki thuat: " + loggedTechnician.getName());
                    session.setAttribute("technician", loggedTechnician);
                    return "redirect:/technician";
                }
            } else
                log.info("Khong co ket qua");

        }
        model.addAttribute("client", client);
        model.addAttribute("failedLogin", "Sai thông tin đăng nhập. Yêu cầu đăng nhập lại");
        // log.info("Null Object");
        return "login";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        Client client = new Client();
        model.addAttribute("newClient", client);
        return "signup";
    }

    @PostMapping("/post_signup")
    public String postSignup(Model model,
            @Valid @ModelAttribute("newClient") Client newClient,
            BindingResult result,
            RedirectAttributes ra) {
        if (result.hasErrors()) {
            model.addAttribute("newClient", newClient);
            return "signup";
        }
        if (clientService.checkUsernameIsExist(newClient)) {
            model.addAttribute("newClient", newClient);
            model.addAttribute("usernameDuplicate", "Tên đăng nhập bị trùng");
            return "signup";
        }
        if (clientService.checkEmailIsExist(newClient)) {
            model.addAttribute("newClient", newClient);
            model.addAttribute("emailDuplicate", "Tài khoản email bị trùng");
            return "signup";
        }
        if (clientService.checkPhoneIsExist(newClient)) {
            model.addAttribute("newClient", newClient);
            model.addAttribute("phoneDuplicate", "Số điện thoại bị trùng");
            return "signup";
        }
        clientService.addNewClient(newClient);
        ra.addFlashAttribute("signupSuccess", "Đăng kí tài khoản thành công");
        log.info("Thong tin khach hang moi: " + newClient.getEmail() + " " + newClient.getPhone());
        return "redirect:/signup";

    }

    @GetMapping("/logout")
    public String logout(Model model, HttpSession session) {

        if (session.getAttribute("client") != null) {
            log.info("Co thong tin khach hang: ");
            session.removeAttribute("client");
        } else {
            log.info("Khong co phien giao dich ");
        }
        if (session.getAttribute("manager") != null) {
            session.removeAttribute("manager");
        } else {
            log.info("Khong co phien giao dich ");
        }
        if (session.getAttribute("technician") != null) {
            session.removeAttribute("technician");
        } else {
            log.info("Khong co phien giao dich ");
        }
        if (session.getAttribute("accountant") != null) {
            session.removeAttribute("accountant");
        } else {
            log.info("Khong co phien giao dich ");
        }
        Client newClient = new Client();
        model.addAttribute("client", newClient);
        return "login";
    }

    /*--------------------------------------------------------------------------------------- */
    @GetMapping("/chart")
    public String showChart(Model model, HttpSession session) throws Exception {
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

    @PostMapping("/save_chart_image")
    public String getImageURL(@RequestParam("image") String image,
            HttpSession session){
        // log.info("Data Image: "+image);
        session.setAttribute("imageURL", image.split(",")[1]);
        return "redirect:/word/export";
        
    }
}
