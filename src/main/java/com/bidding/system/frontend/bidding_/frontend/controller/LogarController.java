/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidding.system.frontend.bidding_.frontend.controller;

import com.bidding.system.frontend.bidding_.frontend.model.UserLogarBean;
import com.bidding.system.frontend.bidding_.frontend.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Aluno
 */
@Controller
public class LogarController {
    @Autowired
    private AuthService authService;

    @GetMapping("/logar")
    public String paginaLogin(Model model) {
        UserLogarBean logar = new UserLogarBean();
        model.addAttribute("user", logar);
        return "logar";
    }
    
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "opa");
        return "home";
    }
    

    @PostMapping("/logar")
    public String fazerLogin(@ModelAttribute UserLogarBean user, HttpSession session) {
        String token = authService.logar(user);
        session.setAttribute("token", token);
        return "redirect:/";
    }
    
}
