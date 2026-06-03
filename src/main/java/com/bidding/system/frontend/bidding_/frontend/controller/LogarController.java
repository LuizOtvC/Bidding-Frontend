/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidding.system.frontend.bidding_.frontend.controller;

import com.bidding.system.frontend.bidding_.frontend.model.UserLogarBean;
import com.bidding.system.frontend.bidding_.frontend.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

/**
 *
 * @author Aluno
 */
@Controller
public class LogarController {
    @Autowired
    private AuthService authService;
    
    
    private String extrairMensagemDeErro(HttpClientErrorException e) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(e.getResponseBodyAsString());
            if (root.has("message")) {
                return root.get("message").asText();
            }
        } catch (Exception ex) {
        }
        return "Ocorreu um erro inesperado na comunicação.";
    }

    @GetMapping("/logar")
    public String paginaLogin(Model model) {
        model.addAttribute("user", new UserLogarBean());
        return "logar";
    }
    
    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        Object token = (String) session.getAttribute("token");
        if(token == null){            
        return "redirect:/logar";
        }
       return "home";
    }  

    @PostMapping("/logar")
    public String fazerLogin(@ModelAttribute UserLogarBean user, HttpSession session, Model model) {
        try {
            String token = authService.logar(user);
            session.setAttribute("token", token);
            String role = authService.extrairRole(token);
            session.setAttribute("role", role);
            return "redirect:/";
        } catch (HttpClientErrorException e) {
            String msg = extrairMensagemDeErro(e);
            model.addAttribute("errorMessage", msg);
            model.addAttribute("credenciais", user);
            return "logar";
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
       session.setAttribute("token", "");
    return "redirect:/logar";
}
}
