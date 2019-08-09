package br.com.WebBakery.model;

import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;

import br.com.WebBakery.abstractClass.AbstractBaseModel;

@Entity
public class Receita extends AbstractBaseModel {

    @Column(length = 32)
    private String nome;

    private Integer quantidade;

    private Time tempoPreparo;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Time getTempoPreparo() {
        return tempoPreparo;
    }

    public void setTempoPreparo(Time tempoPreparo) {
        this.tempoPreparo = tempoPreparo;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

}
