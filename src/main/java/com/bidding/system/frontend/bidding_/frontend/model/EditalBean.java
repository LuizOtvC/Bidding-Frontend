/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidding.system.frontend.bidding_.frontend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Aluno
 */
public class EditalBean {
   private Long id;
    private String titulo;
    private String descricao;
    @JsonProperty("data_fechamento")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime data_fechamento;
    private String status;

    public EditalBean() {
    }

    public EditalBean(Long id, String titulo, String descricao, LocalDateTime data_fechamento, String status) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.data_fechamento = data_fechamento;
        this.status = status;
    }

    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getData_fechamento() {
        return data_fechamento;
    }

    public void setData_fechamento(LocalDateTime data_fechamento) {
        this.data_fechamento = data_fechamento;
    }

    

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

  public String getDataFechamentoFormatada() {
        if (data_fechamento == null) return "-";
        return data_fechamento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
    
    
}
