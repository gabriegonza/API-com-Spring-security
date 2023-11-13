package com.banco.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Transferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "data_transferencia")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime data;

    @NotBlank
    private BigDecimal valor;

    @NotBlank
    private String tipo;

    @Column(name = "nome_operador_transacao", nullable = false, length = 50)
    private String nomeOperadorTransacao;

    @Column(name = "conta_id")
    private long contaId;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getData() {
        return this.data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }


    public BigDecimal getValor() {
        return this.valor;
    }


    public void setValor(@NotBlank BigDecimal valor) {
        this.valor = valor;
    }


    public String getTipo() {
        return this.tipo;
    }


    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNomeOperadorTransacao() {
        return this.nomeOperadorTransacao;
    }


    public void setNomeOperadorTransacao(String nomeOperadorTransacao) {
        this.nomeOperadorTransacao = nomeOperadorTransacao;
    }


    public long getContaId() {
        return this.contaId;
    }

    public void setContaId(long contaId) {
        this.contaId = contaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transferencia that = (Transferencia) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}