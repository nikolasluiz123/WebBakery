package br.com.WebBakery.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.WebBakery.abstractClass.AbstractBaseModel;

@Entity(name = Cidade.TABLE_NAME)
public class Cidade extends AbstractBaseModel {

    public static final String TABLE_NAME = "cidade";
    public static final String FK_NAME = "id_cidade";
    
    private static final String FK_ESTADO_PAIS = Estado.FK_NAME + "_" + Cidade.TABLE_NAME;
    private static final String NOME_CIDADE = "nome" + "_" + Cidade.TABLE_NAME;

    @Column(length = STRING_LENGTH_32C, name = NOME_CIDADE)
    private String nome;

    @ManyToOne
    @JoinColumn(name = FK_ESTADO_PAIS)
    private Estado estado;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

}
