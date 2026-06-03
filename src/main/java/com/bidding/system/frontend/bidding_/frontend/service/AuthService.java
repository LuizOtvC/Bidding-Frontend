/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidding.system.frontend.bidding_.frontend.service;

import com.bidding.system.frontend.bidding_.frontend.model.EditalBean;
import com.bidding.system.frontend.bidding_.frontend.model.UserBean;
import com.bidding.system.frontend.bidding_.frontend.model.UserLogarBean;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Aluno
 */
@Service
public class AuthService {
    private final RestClient restclient;
    

    public AuthService() {
        this.restclient = RestClient.builder()
                .baseUrl("http://localhost:9000")
                .build();
    }
    
   
    
    public String logar(UserLogarBean user) {
        return restclient.post()              
                .uri("/api/auth/logar")                
                .body(user)
                .retrieve()
                .body(String.class);
    }

    public void Registrar(UserBean user) {
        if(!user.getSenha().equals(user.getComfirmarSenha())){
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Senha e comfirmar diferentes");
        }
        user.setRole("FORNECEDOR");
         restclient.post()
                .uri("/api/auth/registrar")
                .body(user)
                .retrieve()
                .body(String.class);
    }
    
    public List<EditalBean> listarEditais(String token, boolean urgente) {
        EditalBean[] editais = restclient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/edital/listar")
                        .queryParam("urgente", urgente)
                        .build())
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(EditalBean[].class);

        return Arrays.asList(editais);
    }
    
    public void CriarEdital(EditalBean edital, String token){
        restclient.post()
                .uri("/api/edital/inserir")
                .header("Authorization", "Bearer " + token)
                .body(edital)
                .retrieve()
                .body(String.class);
    }
    
    public String extrairRole(String token) {
        try {
            String[] partes = token.split("\\.");
            String payload = partes[1];
            int padding = (4 - payload.length() % 4) % 4;
            payload = payload + "=".repeat(padding);
            String json = new String(Base64.getUrlDecoder().decode(payload), StandardCharsets.UTF_8);
            String roleKey = "\"role\":\"";
            int start = json.indexOf(roleKey);
            if (start == -1) return null;
            start += roleKey.length();
            int end = json.indexOf("\"", start);
            return json.substring(start, end);
        } catch (Exception e) {
            return null;
        }
    }
    }
    
