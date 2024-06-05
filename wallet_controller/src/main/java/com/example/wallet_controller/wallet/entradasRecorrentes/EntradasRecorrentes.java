package com.example.wallet_controller.wallet.entradasRecorrentes;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Table(name = "entradas_recorrentes")
@Entity(name = "entradas_recorrentes")
public class EntradasRecorrentes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String descricao;

    @Column(precision = 10, scale = 2)
    private BigDecimal valor;

    private String frequencia;

    private LocalDate data;

    @Column(name = "id_usuario")
    private Integer idUsuario;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(String frequencia) {
        this.frequencia = frequencia;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
}
