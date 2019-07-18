package br.com.WebBakery.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import br.com.WebBakery.abstractClass.AbstractBaseModel;

@Entity
public class Produto extends AbstractBaseModel {

    @Column(length = 50)
    private String descricao;

    private Integer tempoValido;

    private Double preco;

    private boolean ativo;
    @OneToOne
    private Receita receita;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Integer getTempoValido() {
        return tempoValido;
    }

    public void setTempoValido(Integer tempoValido) {
        this.tempoValido = tempoValido;
    }

    public Receita getReceita() {
        return receita;
    }

    public void setReceita(Receita receita) {
        this.receita = receita;
    }

    @Override
    public boolean equals(Object obj) {
        Produto p = (Produto) obj;
        return p.getDescricao().equals(this.descricao);
    }
}
