package br.com.WebBakery.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import br.com.WebBakery.abstractClass.AbstractBaseModel;

@Entity
public class Receita extends AbstractBaseModel {

    @Column(length = 255)
    private String descricao;

    private Integer quantidade;

    private boolean ativo;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
