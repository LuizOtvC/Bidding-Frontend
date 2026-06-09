/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidding.system.frontend.bidding_.frontend.controller;

import com.bidding.system.frontend.bidding_.frontend.model.EditalBean;
import com.bidding.system.frontend.bidding_.frontend.model.LanceBean;
import com.bidding.system.frontend.bidding_.frontend.model.UserLogarBean;
import com.bidding.system.frontend.bidding_.frontend.service.AuthService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

/**
 *
 * @author Aluno
 */
@Controller
public class LanceController {
    @Autowired
    private AuthService editalService;
    
    private String extrairMensagemDeErro(HttpClientErrorException e) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(e.getResponseBodyAsString());
            if (root.has("message")) {
                return root.get("message").asText();
            }
        } catch (Exception ex) {
        }
        return "Erro ao processar a requisição do edital.";
    }


    @GetMapping("/lance/{id}")
public String lance(Model model, HttpSession session, @PathVariable long id) {
    String token = (String) session.getAttribute("token");
    if (token == null) return "redirect:/logar";
    String role = (String) session.getAttribute("role");
    if (!"FORNECEDOR".equals(role)) return "redirect:/editais";
    model.addAttribute("lanceBean", new LanceBean());
    model.addAttribute("id", id);
    model.addAttribute("role", role);
    return "lance";
}

@PostMapping("/lance/{id}")
public String fazerLance(@PathVariable long id, @ModelAttribute LanceBean lance, HttpSession session, Model model) {
    String token = (String) session.getAttribute("token");
    if (token == null) return "redirect:/logar";
    String role = (String) session.getAttribute("role");
    if (!"FORNECEDOR".equals(role)) return "redirect:/editais";
    try {
        editalService.CriarLance(id, lance, token);
        return "redirect:/editais";
    } catch (HttpClientErrorException e) {
        String msg = extrairMensagemDeErro(e);
        model.addAttribute("errorMessage", msg);
        model.addAttribute("lanceBean", lance);
        model.addAttribute("id", id);
        model.addAttribute("role", role);
        return "lance";
    }
}
}
