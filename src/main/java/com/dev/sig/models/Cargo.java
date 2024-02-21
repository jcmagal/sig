package com.dev.sig.models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "cargos")
public class Cargo implements Serializable {

    public enum NivelAcesso {
        NENHUM_ACESSO, ACESSO_FUNCIONARIO, ACESSO_TOTAL
    }


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;

    @Column(precision=10, scale=2, columnDefinition="DECIMAL(10, 2)")
    private BigDecimal vencimento;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private NivelAcesso nivelAcesso;

    public Cargo() {
        super();
    }

    // Getters e setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public NivelAcesso getNivelAcesso() {
        return nivelAcesso;
    }

    public void setNivelAcesso(NivelAcesso nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }

    public BigDecimal getVencimento() {
        return vencimento;
    }

    public void setVencimento(BigDecimal vencimento) {
        this.vencimento = vencimento;
    }
}
