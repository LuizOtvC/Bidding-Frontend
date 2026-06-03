/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidding.system.frontend.bidding_.frontend.controller;

import com.bidding.system.frontend.bidding_.frontend.model.UserBean;
import com.bidding.system.frontend.bidding_.frontend.service.AuthService;
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
public class RegistroController {
    @Autowired
    private AuthService service;

    @GetMapping("/registro")
    public String paginaRegistro(Model model) {
        model.addAttribute("registro", new UserBean());
        return "registro";
    }

    @PostMapping("/registro")
    public String fazerRegistro(@ModelAttribute UserBean user) {
        
           service.Registrar(user);
        return "redirect:/logar";        
        
    }
    
    
}
