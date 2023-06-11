package com.example.demo.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.Accountant;
import com.example.demo.model.Client;
import com.example.demo.model.Manager;
import com.example.demo.model.Staff;
import com.example.demo.model.Technician;
import com.example.demo.service.ClientService;
import com.example.demo.service.StaffService;

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
    @PostMapping("/save_chart_image")
    public String getImageURL(@RequestParam("image") String image,
            HttpSession session){
        // log.info("Data Image: "+image);
        session.setAttribute("imageURL", image.split(",")[1]);
        return "redirect:/word/export";
    }
}
