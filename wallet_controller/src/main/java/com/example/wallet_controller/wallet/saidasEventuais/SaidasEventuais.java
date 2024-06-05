package com.example.wallet_controller.wallet.saidasEventuais;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Table(name = "saidas_eventuais")
@Entity(name = "saidas_eventuais")
public class SaidasEventuais {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  // Mapeado para 'id' INT AUTO_INCREMENT PRIMARY KEY

    private String descricao;  // Mapeado para 'descricao' VARCHAR(255)

    @Column(precision = 10, scale = 2)
    private BigDecimal valor;  // Mapeado para 'valor' DECIMAL(10,2)

    private String frequencia;  // Mapeado para 'frequencia' VARCHAR(50)

    private LocalDate data;  // Mapeado para 'data' DATE

    @Column(name = "id_usuario")
    private Integer idUsuario;  // Mapeado para 'id_usuario' INT

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
