package com.example.wallet_controller.controller.saidas_eventuais;

import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SaidaUsuario {
    @Id
    private Integer id;
    private String descricao;
    private BigDecimal valor;
    private String frequencia;
    private LocalDate data;
    private Integer idUsuario;
    private String tipo;
}
