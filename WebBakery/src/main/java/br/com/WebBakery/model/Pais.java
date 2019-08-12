package br.com.WebBakery.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import br.com.WebBakery.abstractClass.AbstractBaseModel;

@Entity(name = Pais.TABLE_NAME)
public class Pais extends AbstractBaseModel {

    public static final String TABLE_NAME = "paises";
    private static final String NOME_PAIS = "nome_pais";
    private static final String SIGLA_PAIS = "sigla_pais";

    @Column(length = STRING_LENGTH_32C, name = NOME_PAIS)
    private String nome;

    @Column(length = STRING_LENGTH_4C, name = SIGLA_PAIS)
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
