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
import org.springframework.web.bind.annotation.ModelAttribute;
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
public class EditalController {

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


    @GetMapping("/editais")
    public String editais(Model model, HttpSession session,@RequestParam(value = "urgente", required = false, defaultValue = "false") boolean urgente) {
        String token = (String) session.getAttribute("token");
        if (token == null) return "redirect:/logar";

        String role = (String) session.getAttribute("role");

        List<EditalBean> editais = editalService.listarEditais(token, urgente);

        model.addAttribute("editais", editais);
        model.addAttribute("urgente", urgente);
        model.addAttribute("role", role);

        return "editais";
    }
    
    
    @GetMapping("/novoEdital")
    public String novoEdital(Model model, HttpSession session){
        String token = (String) session.getAttribute("token");
        if (token == null) return "redirect:/logar";
        model.addAttribute("editalBean", new EditalBean());
        return "novoEdital";
    }
    
    @PostMapping("/novoEdital")
    public String novoEdital(@ModelAttribute("editalBean") EditalBean edital, HttpSession session, Model model){
        try{
            String token = (String) session.getAttribute("token");
            editalService.CriarEdital(edital, token);
            return "redirect:/editais";
        }catch(HttpClientErrorException e){
            String msg = extrairMensagemDeErro(e);
            model.addAttribute("errorMessage", msg);
            model.addAttribute("editalBean", edital);
            return "novoEdital";
        }
    }
}
    
    
   
