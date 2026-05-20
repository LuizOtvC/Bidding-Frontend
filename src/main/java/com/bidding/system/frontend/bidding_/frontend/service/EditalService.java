/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidding.system.frontend.bidding_.frontend.service;

import com.bidding.system.frontend.bidding_.frontend.model.EditalBean;
import com.bidding.system.frontend.bidding_.frontend.model.UserLogarBean;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Aluno
 */
@Service
public class EditalService {
    
private final RestTemplate restTemplate;
private final String BASE_URL = "http://localhost:9000";

@Autowired
    public EditalService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

public List<EditalBean> listareditais(String token) {
HttpHeaders headers = new HttpHeaders();
headers.setBearerAuth(token);
HttpEntity entity = new HttpEntity(headers);
return restTemplate.exchange(BASE_URL + "/api/edital", HttpMethod.GET, entity, new ParameterizedTypeReference<List<EditalBean>>(){}).getBody();

}
}