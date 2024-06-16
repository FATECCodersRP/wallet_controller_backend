package com.example.wallet_controller.wallet.registro;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import java.time.LocalDate;

@Table(name = "registro")
@Entity(name = "registro")
public class Registro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRegistro;

    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "id_entrada_recorrente")
    private Integer idEntradaRecorrente;

    @Column(name = "id_entrada_eventual")
    private Integer idEntradaEventual;

    @Column(name = "id_saida_recorrente")
    private Integer idSaidaRecorrente;

    @Column(name = "id_saida_eventual")
    private Integer idSaidaEventual;

    private LocalDate data;

    // Getters and Setters
    public Integer getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(Integer idRegistro) {
        this.idRegistro = idRegistro;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdEntradaRecorrente() {
        return idEntradaRecorrente;
    }

    public void setIdEntradaRecorrente(Integer idEntradaRecorrente) {
        this.idEntradaRecorrente = idEntradaRecorrente;
    }

    public Integer getIdEntradaEventual() {
        return idEntradaEventual;
    }

    public void setIdEntradaEventual(Integer idEntradaEventual) {
        this.idEntradaEventual = idEntradaEventual;
    }

    public Integer getIdSaidaRecorrente() {
        return idSaidaRecorrente;
    }

    public void setIdSaidaRecorrente(Integer idSaidaRecorrente) {
        this.idSaidaRecorrente = idSaidaRecorrente;
    }

    public Integer getIdSaidaEventual() {
        return idSaidaEventual;
    }

    public void setIdSaidaEventual(Integer idSaidaEventual) {
        this.idSaidaEventual = idSaidaEventual;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}
