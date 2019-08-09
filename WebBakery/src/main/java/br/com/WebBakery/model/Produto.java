package br.com.WebBakery.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import br.com.WebBakery.abstractClass.AbstractBaseModel;

@Entity
public class Produto extends AbstractBaseModel {

    @Column(length = 50)
    private String descricao;

    private Integer tempoValido;

    private Double preco;

    @OneToOne
    private Receita receita;

    @OneToMany(fetch=FetchType.LAZY, mappedBy="produto", targetEntity=FotoProduto.class, orphanRemoval = false)
    private List<FotoProduto> fotos;

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

    public List<FotoProduto> getFotos() {
        return fotos;
    }

    public void setFotos(List<FotoProduto> fotos) {
        this.fotos = fotos;
    }

    @Override
    public boolean equals(Object obj) {
        Produto p = (Produto) obj;
        return p.getDescricao().equals(this.descricao);
    }
}
