/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidding.system.frontend.bidding_.frontend.controller;


import com.bidding.system.frontend.bidding_.frontend.model.EditalBean;
import com.bidding.system.frontend.bidding_.frontend.service.AuthService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 *
 * @author Aluno
 */
@Controller
public class EditalController {

    @Autowired
    private AuthService editalService;

    @GetMapping("/editais")
    public String listarEditais (HttpSession session, Model model) {
        String token = (String) session.getAttribute("token");
        
        if (token == null) {
        return "redirect:/logar";
    }
        List<EditalBean> editais = editalService.listarEditais(token);
        model.addAttribute("editais", editais);
        return "editais";
    }
    
    
}
    
    
   
