/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidding.system.frontend.bidding_.frontend.service;

import com.bidding.system.frontend.bidding_.frontend.model.EditalBean;
import com.bidding.system.frontend.bidding_.frontend.model.UserBean;
import com.bidding.system.frontend.bidding_.frontend.model.UserLogarBean;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

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
        user.setRole("FORNECEDOR");
        String retorno = restclient
                .post()
                .uri("/api/auth/registrar")
                .body(user)
                .retrieve()
                .body(String.class);
    }
    
    public List<EditalBean> listarEditais(String token) {
        EditalBean[] editais = restclient.get()
                .uri("/api/edital/listar")             
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(EditalBean[].class);        
        return Arrays.asList(editais);
    }
    
    
}
