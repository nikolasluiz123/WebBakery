package br.com.WebBakery.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import br.com.WebBakery.abstractClass.AbstractBaseModel;

@Entity
public class Pais extends AbstractBaseModel {

    @Column(length = 30)
    private String nome;

    @Column(length = 4)
    private String sigla;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

}
