/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidding.system.frontend.bidding_.frontend.service;

import com.bidding.system.frontend.bidding_.frontend.model.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Aluno
 */
@Service
public class RegistroService {
    private final RestTemplate restTemplate;
    private final String BASE_URL = "http://localhost:9000";

    @Autowired
    public RegistroService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void Registrar(UserBean user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserBean> entity = new HttpEntity<>(user, headers);
        restTemplate.postForObject(BASE_URL + "/api/auth/registrar",entity,String.class);
    }
    
    
}
